package musicweb.backend.repository.musicrepository;

import musicweb.backend.entity.musicentity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntity, String> {
    List<ArtistEntity> findByArtistNameContainingOrderByArtistPopularityDesc(String name);
}
