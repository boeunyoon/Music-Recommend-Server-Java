package musicweb.backend.service.musicservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import musicweb.backend.dto.MemberResponseDto;
import musicweb.backend.entity.musicentity.SongEntity;
import musicweb.backend.repository.musicrepository.SongRepository;
import musicweb.backend.service.MemberService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final RestTemplate restTemplate;
    private final MemberService memberService;
    private final SongSearchService songSearchService;
    private final SongRepository songRepository;
    private final PlayListService playListService;
    private final ArtistSongService artistSongService;
    //추천시스템에 들어갈 유저정보 만들기
    private JSONObject makeUserinfoJson(){
        MemberResponseDto infoBySecurity = memberService.getInfoBySecurity();
        JSONObject jsonObject = new JSONObject();
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", infoBySecurity.getEmail());
        userInfo.put("nickname", infoBySecurity.getNickname());
        jsonObject.put("user_info", userInfo);
        return jsonObject;
    }
    //플레이리스트가 없을 경우 추천해 주는 음악(Track추천)
    public JSONObject getFirstArtistList(){
        JSONObject combineList = makeUserinfoJson();
        JSONObject artistList = new JSONObject();
        List<String> resultList = new ArrayList<>();
        MemberResponseDto infoBySecurity = memberService.getInfoBySecurity();
        String preferenceGenre = infoBySecurity.getPreferenceGenre();
        //"[Taylor Swift, The Weeknd, Drake, Lana Del Rey, Ed Sheeran]"를  ["Taylor Swift", "The Weeknd", "Drake", "Lana Del Rey", "Ed Sheeran"]이렇게 바꾼다.
        //문자열 -> 배열
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(preferenceGenre);
        // 정규 표현식에 맞는 부분이 있다면
        if (matcher.find()) {
            String content = matcher.group(1); // 대괄호 안의 내용 추출
            String[] elements = content.split(", "); // 쉼표와 공백으로 분리
            resultList = Arrays.asList(elements); // 배열을 리스트로 변환
        }
        artistList.put("artist_list",resultList);
        combineList.put("input", artistList);
        return combineList;
    }
    //추천 시스템에 보낼 나의 플레이리스트
    public JSONObject makeTrackListForm(){
        JSONObject combineList = makeUserinfoJson();
        List<String> SongIdList = new ArrayList<>();
        JSONObject trackList = new JSONObject();
        String id = "";
        List<SongEntity> userDetailPlaylist = playListService.getUserDetailPlaylist();
        if (userDetailPlaylist.isEmpty()){
            List<String> songs = artistSongService.GetPreferArtistSongs();
            trackList.put("track_list", songs);
            combineList.put("input", trackList);
        } else {
            for (SongEntity songEntity : userDetailPlaylist) {
                id = songEntity.getId();
                SongIdList.add(id);
            }
            trackList.put("track_list", SongIdList);
            combineList.put("input", trackList);
        }
        return combineList;
    }
    //플레이리스트 존재 여부에 따른 트랙 추천 Json만들기
    public ResponseEntity<?> finalJsonForm(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<SongEntity> userDetailPlaylist = playListService.getUserDetailPlaylist();
        if(userDetailPlaylist.isEmpty()){
            String url = "http://127.0.0.1:8000/first_select";
            JSONObject firstArtistList = getFirstArtistList();
            HttpEntity<JSONObject> request = new HttpEntity<>(firstArtistList, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()){
                return response;
            }else {
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }else {
            String url = "http://127.0.0.1:8000/recommand";
            JSONObject jsonObject = makeTrackListForm();
            HttpEntity<JSONObject> request = new HttpEntity<>(jsonObject, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()){
                return response;
            }else {
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
    }
    //플레이리스트 추천
    public ResponseEntity<?> recommendPlaylist(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = "http://127.0.0.1:8000/playlist_recommand";
        JSONObject playlist = makeTrackListForm();
        System.out.println("playlist = " + playlist);
        HttpEntity<JSONObject> request = new HttpEntity<>(playlist, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        if (response.getStatusCode().is2xxSuccessful()){
            String body = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            try{
                JsonNode jsonNode = objectMapper.readTree(body);
                JsonNode recommend_playlists = jsonNode.get("recommand_playlists");
                ArrayNode arrayNode = (ArrayNode) recommend_playlists;
                for (JsonNode node : arrayNode) {
                    String track_list = node.get("track_list").asText();
                    String[] trackListArray = track_list.replaceAll("[\\[\\]\\s\"]", "").split(",");

                    List<SongEntity> songEntityList = new ArrayList<>();

                    for (String track : trackListArray) {
                        track = track.replaceAll("[\"']", "");
                        Optional<SongEntity> optionalTrack = songRepository.findById(track);
                        if (optionalTrack.isPresent()){
                            SongEntity songEntity = optionalTrack.get();
                            songEntityList.add(songEntity);
                        }
                    }
                    ArrayNode trackArrayNode = objectMapper.createArrayNode();
                    for (SongEntity songEntity : songEntityList) {
                        ObjectNode trackJson = objectMapper.createObjectNode();
                        trackJson.put("id", songEntity.getId());
                        trackJson.put("albumId", songEntity.getAlbumId());
                        trackJson.put("album", songEntity.getAlbum());
                        trackJson.put("title", songEntity.getTitle());
                        trackJson.put("artist", songEntity.getArtist());
                        trackJson.put("artistId", songEntity.getArtistId());
                        trackJson.put("releaseDate", songEntity.getReleaseDate());
                        trackJson.put("popularity", songEntity.getPopularity());
                        trackJson.put("image640", songEntity.getImage640());
                        trackJson.put("image300",songEntity.getImage300());
                        trackJson.put("image64", songEntity.getImage64());

                        trackArrayNode.add(trackJson);
                    }
                    ((ObjectNode) node).set("track_list", trackArrayNode);
                }
                String modifiedJsonData = objectMapper.writeValueAsString(jsonNode);
                return new ResponseEntity<>(modifiedJsonData, HttpStatus.OK);
            }catch (JsonProcessingException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Error processing JSON", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //선호 장르
    public ResponseEntity<?> recommendGenreAndArtist(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = "http://127.0.0.1:8000/prefer_genre";
        JSONObject jsonObject = makeTrackListForm();
        HttpEntity<JSONObject> request = new HttpEntity<>(jsonObject, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        if (response.getStatusCode().is2xxSuccessful()){
            JSONObject jsonObject1 = ParseJsonObject(response);
            return new ResponseEntity<>(jsonObject1, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    // 받아온 장르 선호도 데이터에 track 정보 붙여서 보내주기
    private JSONObject ParseJsonObject(ResponseEntity<String> response){
        JSONParser jsonParser = new JSONParser();
        JSONArray genrePlaylists = new JSONArray();
        JSONObject responseBody = new JSONObject();
        List<Integer> emptyList = new ArrayList<>();
        try{
            responseBody = (JSONObject) jsonParser.parse(response.getBody());
            List<String> genre = (List<String>) responseBody.get("top_genre");
            genrePlaylists = (JSONArray) responseBody.get("genre_playlists");
            for (int i = 0; i < genrePlaylists.size(); i++) {
                String s1 = genre.get(i);
                JSONObject genrePlaylistsJSON = (JSONObject) genrePlaylists.get(i);
                JSONArray GenrePlaylistArray = (JSONArray)genrePlaylistsJSON.get(s1);
                System.out.println("GenrePlaylistArray = " + GenrePlaylistArray);
                if (GenrePlaylistArray.size() != 0 ){
                    for (Object trackList : GenrePlaylistArray) {
                        JSONObject trackList1 = (JSONObject) trackList;
                        List<String> tracks = (List<String>)trackList1.get("track_list");
                        JSONArray trackArray = new JSONArray();
                        for (String track : tracks) {
                            System.out.println("track = " + track);
                            JSONObject trackJson = new JSONObject();
                            Optional<SongEntity> songEntityOptional = songRepository.findById(track);
                            if (songEntityOptional.isPresent()){
                                SongEntity songEntity = songEntityOptional.get();
                                trackJson.put("id", songEntity.getId());
                                trackJson.put("albumId", songEntity.getAlbumId());
                                trackJson.put("album", songEntity.getAlbum());
                                trackJson.put("title", songEntity.getTitle());
                                trackJson.put("artist", songEntity.getArtist());
                                trackJson.put("artistId", songEntity.getArtistId());
                                trackJson.put("releaseDate", songEntity.getReleaseDate());
                                trackJson.put("popularity", songEntity.getPopularity());
                                trackJson.put("image640", songEntity.getImage640());
                                trackJson.put("image300",songEntity.getImage300());
                                trackJson.put("image64", songEntity.getImage64());
                                trackArray.add(trackJson);
                            }
                        }
                        trackList1.replace("track_list", trackArray);
                    }
                }else {
                    genrePlaylistsJSON.remove(s1);
                    emptyList.add(i);
                }
            }
            Collections.sort(emptyList, Collections.reverseOrder());
            for (Integer j : emptyList) {
                genrePlaylists.remove(j.intValue());
                genre.remove(j.intValue());
            }
            responseBody.replace("top_genre", genre);
            responseBody.replace("genre_playlists", genrePlaylists);
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseBody;
    }
    //선호 아티스트 플레이리스트
    public ResponseEntity<?> recommendArtistPlaylist(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = "http://127.0.0.1:8000/prefer_artist";
        JSONObject jsonObject = makeTrackListForm();
        HttpEntity<JSONObject> request = new HttpEntity<>(jsonObject, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        if (response.getStatusCode().is2xxSuccessful()){
            JSONObject jsonObject1 = ParseJsonObjectArtist(response);
            return new ResponseEntity<>(jsonObject1, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    // 받아온 아티스트 선호도 데이터에 track 정보 붙여서 보내주기
    private JSONObject ParseJsonObjectArtist(ResponseEntity<String> response){
        JSONParser jsonParser = new JSONParser();
        JSONArray artistPlaylists = new JSONArray();
        JSONObject responseBody = new JSONObject();
        List<Integer> emptyList = new ArrayList<>();
        try{
            responseBody = (JSONObject) jsonParser.parse(response.getBody());
            List<String> topArtist = (List<String>) responseBody.get("top_artist");
            artistPlaylists = (JSONArray) responseBody.get("artist_playlist");
            for (int i = 0; i < artistPlaylists.size(); i++) {
                String s1 = topArtist.get(i);
                JSONObject artistPlaylistsJSON = (JSONObject) artistPlaylists.get(i);
                JSONArray ArtistPlaylistArray = (JSONArray)artistPlaylistsJSON.get(s1);
                if (ArtistPlaylistArray.size() != 0 ){
                    for (Object trackList : ArtistPlaylistArray) {
                        JSONObject trackList1 = (JSONObject) trackList;
                        List<String> tracks = (List<String>)trackList1.get("track_list");
                        JSONArray trackArray = new JSONArray();
                        for (String track : tracks) {
                            System.out.println("track = " + track);
                            JSONObject trackJson = new JSONObject();
                            Optional<SongEntity> songEntityOptional = songRepository.findById(track);
                            SongEntity songEntity = songEntityOptional.get();
                            trackJson.put("id", songEntity.getId());
                            trackJson.put("albumId", songEntity.getAlbumId());
                            trackJson.put("album", songEntity.getAlbum());
                            trackJson.put("title", songEntity.getTitle());
                            trackJson.put("artist", songEntity.getArtist());
                            trackJson.put("artistId", songEntity.getArtistId());
                            trackJson.put("releaseDate", songEntity.getReleaseDate());
                            trackJson.put("popularity", songEntity.getPopularity());
                            trackJson.put("image640", songEntity.getImage640());
                            trackJson.put("image300",songEntity.getImage300());
                            trackJson.put("image64", songEntity.getImage64());
                            trackArray.add(trackJson);
                        }
                        trackList1.replace("track_list", trackArray);
                    }
                }else {
                    artistPlaylistsJSON.remove(s1);
                    emptyList.add(i);
                }
            }
            Collections.sort(emptyList, Collections.reverseOrder());
            for (Integer j : emptyList) {
                artistPlaylists.remove(j.intValue());
                topArtist.remove(j.intValue());
            }
            responseBody.replace("top_artist", topArtist);
            responseBody.replace("artist_playlist", artistPlaylists);
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseBody;
    }
}