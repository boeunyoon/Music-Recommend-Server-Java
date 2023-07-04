package musicweb.backend.service.musicservice;

import lombok.RequiredArgsConstructor;
import musicweb.backend.dto.ArtistDTO;
import musicweb.backend.dto.MemberResponseDto;
import musicweb.backend.entity.Member;
import musicweb.backend.entity.musicentity.ArtistEntity;
import musicweb.backend.repository.UserRepository;
import musicweb.backend.repository.musicrepository.ArtistRepository;
import musicweb.backend.service.MemberService;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SelectGenreService {
    private final ArtistRepository artistRepository;
    private final MemberService memberService;
    private final UserRepository userRepository;

    public JSONObject getAllArtist(){
        List<ArtistEntity> artistEntityList = artistRepository.findAll(Sort.by(Sort.Direction.DESC, "artistPopularity"));
        MemberResponseDto infoBySecurity = memberService.getInfoBySecurity();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_info", infoBySecurity);
        jsonObject.put("artist_info", artistEntityList);
        return jsonObject;
    }

    public List<ArtistEntity> getArtist(String artistName){
        List<ArtistEntity> artist = artistRepository.findByArtistNameContainingOrderByArtistPopularityDesc(artistName);
        return artist;
    }
    public List<String> receiveArtistListService(List<ArtistDTO> artistDTOList){
        List<String> artistList = new ArrayList<>();
        for (ArtistDTO artist: artistDTOList){
            artistList.add(artist.getArtistName());
        }
        MemberResponseDto infoBySecurity = memberService.getInfoBySecurity();
        String email = infoBySecurity.getEmail();
        Optional<Member> member = userRepository.findByEmail(email);
        Member member1 = member.orElseGet(() -> {
            Member newMember = new Member();
            newMember.setPreferenceGenre(artistList.toString());
            return newMember;
        });
        member1.setPreferenceGenre(artistList.toString());
        userRepository.save(member1);
        return artistList;
    }
}
