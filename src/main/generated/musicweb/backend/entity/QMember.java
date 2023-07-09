package musicweb.backend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 554435500L;

    public static final QMember member = new QMember("member1");

    public final EnumPath<Authority> authority = createEnum("authority", Authority.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<musicweb.backend.entity.musicentity.PlaylistEntity, musicweb.backend.entity.musicentity.QPlaylistEntity> playlists = this.<musicweb.backend.entity.musicentity.PlaylistEntity, musicweb.backend.entity.musicentity.QPlaylistEntity>createList("playlists", musicweb.backend.entity.musicentity.PlaylistEntity.class, musicweb.backend.entity.musicentity.QPlaylistEntity.class, PathInits.DIRECT2);

    public final StringPath PreferenceGenre = createString("PreferenceGenre");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

