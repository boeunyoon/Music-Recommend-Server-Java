package musicweb.backend.entity.musicentity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "artist")
@Entity
@Getter
public class ArtistEntity {
    @Id
    @Column(name = "id")
    private String artistId;
    @Column(name = "name")
    private String artistName;
    @Column(name = "popularity")
    private String artistPopularity;
    @Column(name = "image")
    private String artistImage;
}
