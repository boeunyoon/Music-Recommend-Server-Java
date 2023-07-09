package musicweb.backend.entity.musicentity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaylistEntity is a Querydsl query type for PlaylistEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaylistEntity extends EntityPathBase<PlaylistEntity> {

    private static final long serialVersionUID = 1189672141L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaylistEntity playlistEntity = new QPlaylistEntity("playlistEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final musicweb.backend.entity.QMember member;

    public final StringPath songId = createString("songId");

    public final ListPath<SongEntity, QSongEntity> songs = this.<SongEntity, QSongEntity>createList("songs", SongEntity.class, QSongEntity.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QPlaylistEntity(String variable) {
        this(PlaylistEntity.class, forVariable(variable), INITS);
    }

    public QPlaylistEntity(Path<? extends PlaylistEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaylistEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaylistEntity(PathMetadata metadata, PathInits inits) {
        this(PlaylistEntity.class, metadata, inits);
    }

    public QPlaylistEntity(Class<? extends PlaylistEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new musicweb.backend.entity.QMember(forProperty("member")) : null;
    }

}

