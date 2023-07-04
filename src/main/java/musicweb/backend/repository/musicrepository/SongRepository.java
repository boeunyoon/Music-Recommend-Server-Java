package musicweb.backend.repository.musicrepository;

import musicweb.backend.entity.musicentity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, String> {
}
