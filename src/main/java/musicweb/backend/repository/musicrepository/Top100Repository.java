package musicweb.backend.repository.musicrepository;

import musicweb.backend.entity.musicentity.Top100;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Top100Repository extends JpaRepository<Top100, String> {
}
