package musicweb.backend.service.musicservice;

import lombok.RequiredArgsConstructor;
import musicweb.backend.dto.MemberResponseDto;
import musicweb.backend.entity.Member;
import musicweb.backend.entity.musicentity.PlaylistEntity;
import musicweb.backend.entity.musicentity.SongEntity;
import musicweb.backend.repository.UserRepository;
import musicweb.backend.repository.musicrepository.PlaylistRepository;
import musicweb.backend.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayListService {
    private final PlaylistRepository playlistRepository;
    private final MemberService memberService;
    private final UserRepository userRepository;
    private final SongSearchService songSearchService;
    //사용자의 플레이리스트에 노래를 추가
    @Transactional
    public void createPlaylist(Member member, String title, String songId) {
        boolean isDuplicate = playlistRepository.existsByMemberAndSongId(member,songId);
        if(isDuplicate){
            //노래가 사용자의 플레이리스트에 있는 경우 플레이리스트에서 삭제
            playlistRepository.deleteByMemberAndSongId(member, songId);
        }else {
            PlaylistEntity playlistEntity = new PlaylistEntity();
            playlistEntity.setTitle(title);
            playlistEntity.setSongId(songId);
            playlistEntity.setMember(member);
            playlistRepository.save(playlistEntity);
        }
    }
    //유저의 플레이리스트 조회
    public List<PlaylistEntity> getUserPlaylist(){
        MemberResponseDto infoBySecurity = memberService.getInfoBySecurity();
        String email = infoBySecurity.getEmail();
        Optional<Member> byEmail = userRepository.findByEmail(email);
        Member member = byEmail.get();
        List<PlaylistEntity> allByMember = playlistRepository.findAllByMember(member);
        return allByMember;
    }
    //유저의 플레이스트 조회 플레이리스트 화면에 띄울 때 더 많은 정보가 필요하기 떄문에 새로 만듬
    public List<SongEntity> getUserDetailPlaylist(){
        MemberResponseDto infoBySecurity = memberService.getInfoBySecurity();
        String email = infoBySecurity.getEmail();
        Optional<Member> byEmail = userRepository.findByEmail(email);
        Member member = byEmail.get();
        List<PlaylistEntity> allByMember = playlistRepository.findAllByMember(member);
        List<SongEntity> songEntityList = songSearchService.searchByPlaylistSongID(allByMember);
        return songEntityList;
    }

}
