package musicweb.backend.entity.musicentity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTop100 is a Querydsl query type for Top100
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTop100 extends EntityPathBase<Top100> {

    private static final long serialVersionUID = -1969897228L;

    public static final QTop100 top100 = new QTop100("top100");

    public final StringPath billboard_data = createString("billboard_data");

    public final StringPath billboard_date = createString("billboard_date");

    public QTop100(String variable) {
        super(Top100.class, forVariable(variable));
    }

    public QTop100(Path<? extends Top100> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTop100(PathMetadata metadata) {
        super(Top100.class, metadata);
    }

}

