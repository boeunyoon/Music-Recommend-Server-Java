package musicweb.backend.entity.musicentity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "songs")
@Entity
@Getter
public class SongEntity {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "title")
    private String title;
    @Column(name = "artist")
    private String artist;
    @Column(name = "artist_id")
    private String artistId;
    @Column(name = "album")
    private String album;
    @Column(name = "album_id")
    private String albumId;
    @Column(name = "release_date")
    private String releaseDate;
    @Column(name = "popularity")
    private String popularity;
    @Column(name = "640px")
    private String image640;
    @Column(name = "300px")
    private String image300;
    @Column(name = "64px")
    private String image64;
}
