package musicweb.backend.entity.musicentity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSongEntity is a Querydsl query type for SongEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSongEntity extends EntityPathBase<SongEntity> {

    private static final long serialVersionUID = 1662952912L;

    public static final QSongEntity songEntity = new QSongEntity("songEntity");

    public final StringPath album = createString("album");

    public final StringPath albumId = createString("albumId");

    public final StringPath artist = createString("artist");

    public final StringPath artistId = createString("artistId");

    public final StringPath id = createString("id");

    public final StringPath image300 = createString("image300");

    public final StringPath image64 = createString("image64");

    public final StringPath image640 = createString("image640");

    public final StringPath popularity = createString("popularity");

    public final StringPath releaseDate = createString("releaseDate");

    public final StringPath title = createString("title");

    public QSongEntity(String variable) {
        super(SongEntity.class, forVariable(variable));
    }

    public QSongEntity(Path<? extends SongEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSongEntity(PathMetadata metadata) {
        super(SongEntity.class, metadata);
    }

}

