package musicweb.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;
    @Enumerated(EnumType.STRING)
    private Authority authority;
    @Column
    private String PreferenceGenre;

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPreferenceGenre(String preferenceGenre) {
        PreferenceGenre = preferenceGenre;
    }

    @Builder
    public Member(Long id, String email, String password, String nickname, Authority authority, String PreferenceGenre) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.authority = authority;
        this.PreferenceGenre = PreferenceGenre;
    }

}
