package musicweb.backend.entity.musicentity;

import lombok.Getter;
import lombok.Setter;
import musicweb.backend.entity.Member;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "playlists")
@Getter @Setter
public class PlaylistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "song_id", unique = true, nullable = false)
    private String songId;
    @Column(nullable = false)
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "playlist_songs",
            joinColumns = @JoinColumn(name = "playlists_id"),
            inverseJoinColumns = @JoinColumn(name = "music_id")
    )
    private List<SongEntity> songs;
}
