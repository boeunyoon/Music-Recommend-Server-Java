package musicweb.backend.service.musicservice;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import musicweb.backend.entity.musicentity.ArtistEntity;
import musicweb.backend.entity.musicentity.QArtistEntity;
import musicweb.backend.entity.musicentity.QSongEntity;
import musicweb.backend.entity.musicentity.SongEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongSearchService {
    private final JPAQueryFactory jpaQueryFactory;
    private final QSongEntity qSongEntity = QSongEntity.songEntity;
    private final QArtistEntity qArtistEntity = QArtistEntity.artistEntity;

    public SongSearchService(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<SongEntity> searchByArtistAndTitle(String keyword) {
        // 공백으로 키워드를 분리하여 각각의 토큰으로 처리
        String[] keywords = keyword.split("\\s+");

        BooleanExpression artistPredicate = null;
        BooleanExpression titlePredicate = null;

        for (String token : keywords) {
            if (artistPredicate == null) {
                artistPredicate = qSongEntity.artist.containsIgnoreCase(token);
            } else {
                artistPredicate = artistPredicate.and(qSongEntity.artist.containsIgnoreCase(token));
            }

            if (titlePredicate == null) {
                titlePredicate = qSongEntity.title.containsIgnoreCase(token);
            } else {
                titlePredicate = titlePredicate.and(qSongEntity.title.containsIgnoreCase(token));
            }
        }

        return jpaQueryFactory
                .selectFrom(qSongEntity)
                .where(artistPredicate.or(titlePredicate))
                .orderBy(qSongEntity.popularity.desc())
                .fetch();
    }


}
