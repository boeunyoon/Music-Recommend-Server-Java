package musicweb.backend.repository;

import musicweb.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    //이메일이 존재하는지 판별하는 로직
    boolean existsByEmail(String email);
}
