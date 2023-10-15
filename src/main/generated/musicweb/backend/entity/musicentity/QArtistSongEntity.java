package musicweb.backend.entity.musicentity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QArtistSongEntity is a Querydsl query type for ArtistSongEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArtistSongEntity extends EntityPathBase<ArtistSongEntity> {

    private static final long serialVersionUID = -44107145L;

    public static final QArtistSongEntity artistSongEntity = new QArtistSongEntity("artistSongEntity");

    public final StringPath artistName = createString("artistName");

    public final StringPath id = createString("id");

    public final StringPath Songs = createString("Songs");

    public QArtistSongEntity(String variable) {
        super(ArtistSongEntity.class, forVariable(variable));
    }

    public QArtistSongEntity(Path<? extends ArtistSongEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArtistSongEntity(PathMetadata metadata) {
        super(ArtistSongEntity.class, metadata);
    }

}

