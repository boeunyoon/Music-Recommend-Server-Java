package musicweb.backend.entity.musicentity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QArtistEntity is a Querydsl query type for ArtistEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArtistEntity extends EntityPathBase<ArtistEntity> {

    private static final long serialVersionUID = -999529342L;

    public static final QArtistEntity artistEntity = new QArtistEntity("artistEntity");

    public final StringPath artistId = createString("artistId");

    public final StringPath artistImage = createString("artistImage");

    public final StringPath artistName = createString("artistName");

    public final StringPath artistPopularity = createString("artistPopularity");

    public QArtistEntity(String variable) {
        super(ArtistEntity.class, forVariable(variable));
    }

    public QArtistEntity(Path<? extends ArtistEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArtistEntity(PathMetadata metadata) {
        super(ArtistEntity.class, metadata);
    }

}

