package musicweb.backend.service.musicservice;

import lombok.RequiredArgsConstructor;
import musicweb.backend.dto.MemberResponseDto;
import musicweb.backend.entity.musicentity.SongEntity;
import musicweb.backend.service.MemberService;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final RestTemplate restTemplate;
    private final MemberService memberService;
    private final SongSearchService songSearchService;
    private final PlayListService playListService;
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
    //플레이리스트가 없을 경우 추천해 주는 음악
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
        for (SongEntity songEntity : userDetailPlaylist) {
            id = songEntity.getId();
            SongIdList.add(id);
        }
        trackList.put("track_list", SongIdList);
        combineList.put("input", trackList);
        return combineList;
    }
    //플레이리스트 존재 여부에 따른 Json만들기
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
}
