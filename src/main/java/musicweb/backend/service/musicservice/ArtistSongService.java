package musicweb.backend.service.musicservice;

import lombok.RequiredArgsConstructor;
import musicweb.backend.dto.MemberResponseDto;
import musicweb.backend.entity.musicentity.ArtistSongEntity;
import musicweb.backend.repository.musicrepository.ArtistSongRepository;
import musicweb.backend.service.MemberService;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ArtistSongService {
    private final MemberService memberService;
    private final PlayListService playListService;
    private final ArtistSongRepository artistSongRepository;
    //회원의 플레이리스트가 없는 경우 여기에서 추천이 가능하게 만들어준다.
    //회원이 고른 선호하는 아티스트를 가져와 트랙리스트를 불러온다.
    private List<String> GetPreferArtistList(){
        List<String> resultList = new ArrayList<>();
        MemberResponseDto infoBySecurity = memberService.getInfoBySecurity();
        String preferenceGenre = infoBySecurity.getPreferenceGenre();
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(preferenceGenre);
        if (matcher.find()) {
            String content = matcher.group(1);
            String[] elements = content.split(", ");
            resultList = Arrays.asList(elements);
        }
        return resultList;
    }
    public List<String> GetPreferArtistSongs(){
        List<String> artistList = GetPreferArtistList();
        List<String> Playlist = new ArrayList<>();
        for (String name : artistList) {
            Optional<ArtistSongEntity> byArtistName = artistSongRepository.findByArtistName(name);
            if (byArtistName.isPresent()){
                String songs = byArtistName.get().getSongs();
                Pattern pattern = Pattern.compile("'(.*?)'");
                Matcher matcher = pattern.matcher(songs);
                // 정규식과 일치하는 부분을 찾아 List에 추가
                List<String> songIds = new ArrayList<>();
                while (matcher.find()) {
                    String match = matcher.group(1); // 첫 번째 그룹을 가져옴
                    songIds.add(match);
                }
                Playlist.addAll(songIds);
            }else {
                throw new NoSuchElementException();
            }
        }
        return Playlist;
    }
}
