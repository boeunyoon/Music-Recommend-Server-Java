package musicweb.backend.entity.musicentity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "artist_songs")
@Getter
public class ArtistSongEntity {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String artistName;
    @Column(name = "songs")
    private String Songs;

}
