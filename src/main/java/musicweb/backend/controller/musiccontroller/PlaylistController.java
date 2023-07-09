package musicweb.backend.controller.musiccontroller;

import lombok.RequiredArgsConstructor;
import musicweb.backend.dto.MemberResponseDto;
import musicweb.backend.entity.Member;
import musicweb.backend.entity.musicentity.PlaylistEntity;
import musicweb.backend.entity.musicentity.SongEntity;
import musicweb.backend.service.MemberService;
import musicweb.backend.service.musicservice.PlayListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PlaylistController {
    private final PlayListService playListService;
    private final MemberService memberService;
    @PostMapping("/addplaylist")
    public ResponseEntity<?> addPlaylist(
            @RequestBody SongEntity song
            ){
        MemberResponseDto infoBySecurity = memberService.getInfoBySecurity();
        String email = infoBySecurity.getEmail();
        Member memberEmail = memberService.getMemberEmail(email);
        String title = song.getTitle();
        String songId = song.getId();
        playListService.createPlaylist(memberEmail, title, songId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/getplaylist")
    public ResponseEntity<List<PlaylistEntity>> getPlaylist(){
        List<PlaylistEntity> userPlaylist = playListService.getUserPlaylist();
        return ResponseEntity.ok(userPlaylist);
    }
    @GetMapping("/playlist")
    public ResponseEntity<List<SongEntity>> getUserPlaylist(){
        List<SongEntity> userPlaylist = playListService.getUserDetailPlaylist();
        return ResponseEntity.ok(userPlaylist);
    }
}
