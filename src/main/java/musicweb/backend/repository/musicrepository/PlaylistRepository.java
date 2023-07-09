package musicweb.backend.repository.musicrepository;

import musicweb.backend.entity.Member;
import musicweb.backend.entity.musicentity.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long> {
    boolean existsByMemberAndSongId(Member member, String sonId);
    void deleteByMemberAndSongId(Member member, String songId);
    List<PlaylistEntity> findAllByMember(Member member);
}
