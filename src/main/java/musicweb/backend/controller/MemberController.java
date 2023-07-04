package musicweb.backend.controller;

import lombok.RequiredArgsConstructor;
import musicweb.backend.dto.ChangePasswordRequestDto;
import musicweb.backend.dto.MemberRequestDto;
import musicweb.backend.dto.MemberResponseDto;
import musicweb.backend.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyMemberInfo(){
        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberResponseDto infoBySecurity = memberService.getInfoBySecurity();
        System.out.println(infoBySecurity.getNickname());
        return ResponseEntity.ok((infoBySecurity));
    }
    @PostMapping("/nickname")
    public ResponseEntity<MemberResponseDto> setMemberNickname(@RequestBody MemberRequestDto requestDto){
        return ResponseEntity.ok(memberService.changeMemberNickname(requestDto.getEmail(), requestDto.getNickname()));
    }
    @PostMapping("/password")
    public ResponseEntity<MemberResponseDto> setMemberPassword(@RequestBody ChangePasswordRequestDto requestDto){
        return ResponseEntity.ok(memberService.changeMemberPassword(requestDto.getExPassword(), requestDto.getNewPassword()));
    }
}
