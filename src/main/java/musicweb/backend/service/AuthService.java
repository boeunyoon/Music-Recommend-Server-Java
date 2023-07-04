package musicweb.backend.service;

import lombok.RequiredArgsConstructor;
import musicweb.backend.dto.MemberRequestDto;
import musicweb.backend.dto.MemberResponseDto;
import musicweb.backend.dto.TokenDto;
import musicweb.backend.entity.Member;
import musicweb.backend.jwt.TokenProvider;
import musicweb.backend.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository memberRepository;
    private final AuthenticationManagerBuilder managerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    //회원 가입
    public MemberResponseDto signUp(MemberRequestDto memberRequestDto){
        if(memberRepository.existsByEmail(memberRequestDto.getEmail())){
            throw new RuntimeException("이미 가입되어있는 사용자입니다");
        }
        Member member = memberRequestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }
    //로그인
    /*
        login 메소드는MemberRequestDto에 있는 메소드 toAuthentication를 통해 생긴 UsernamePasswordAuthenticationToken 타입의 데이터를 가지게된다.
        주입받은 Builder를 통해 AuthenticationManager를 구현한 ProviderManager를 생성한다.
        이후 ProviderManager는 데이터를 AbstractUserDetailsAuthenticationProvider 의 자식 클래스인 DaoAuthenticationProvider 를 주입받아서 호출한다.
        DaoAuthenticationProvider 내부에 있는 authenticate에서 retrieveUser을 통해 DB에서의 User의 비밀번호가 실제 비밀번호가 맞는지 비교한다.
        retrieveUser에서는 DB에서의 User를 꺼내기 위해, CustomUserDetailService에 있는 loadUserByUsername을 가져와 사용한다.
     */
    public TokenDto login(MemberRequestDto requestDto){
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        Authentication authenticate = managerBuilder.getObject().authenticate(authenticationToken);
        return tokenProvider.generateTokenDto(authenticate);
    }
}
