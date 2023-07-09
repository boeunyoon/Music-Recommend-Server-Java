package musicweb.backend.service;

import lombok.RequiredArgsConstructor;
import musicweb.backend.config.SecurityUtil;
import musicweb.backend.dto.MemberResponseDto;
import musicweb.backend.entity.Member;
import musicweb.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final UserRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponseDto getInfoBySecurity(){
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(()->new RuntimeException("로그인 유저 정보가 없습니다."));
    }
    public Member getMemberEmail(String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new RuntimeException("로그인 유저 정보가 없습니다"));
        return member;
    }
    @Transactional
    public MemberResponseDto changeMemberNickname(String email, String nickname){
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new RuntimeException("로그인 유저 정보가 없습니다"));
        member.setNickname(nickname);
        return MemberResponseDto.of(memberRepository.save(member));
    }
    @Transactional
    public MemberResponseDto changeMemberPassword(String exPassword, String newPassword){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
        if(!passwordEncoder.matches(exPassword, member.getPassword())){
            throw new RuntimeException("비밀번호가 맞지 않습니다.");
        }
        member.setPassword(passwordEncoder.encode((newPassword)));
        return MemberResponseDto.of(memberRepository.save(member));
    }
}
