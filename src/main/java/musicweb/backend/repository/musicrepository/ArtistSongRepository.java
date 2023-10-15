package musicweb.backend.repository.musicrepository;

import musicweb.backend.entity.musicentity.ArtistSongEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistSongRepository extends JpaRepository<ArtistSongEntity, String> {
    Optional<ArtistSongEntity> findByArtistName(String name);
}
