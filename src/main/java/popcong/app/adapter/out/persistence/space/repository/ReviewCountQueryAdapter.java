package popcong.app.adapter.out.persistence.space.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import popcong.app.adapter.out.persistence.space.entity.QReservationJpaEntity;
import popcong.app.adapter.out.persistence.space.entity.QReservationReviewJpaEntity;
import popcong.app.adapter.out.persistence.space.entity.QSpaceJpaEntity;
import popcong.app.application.space.port.out.ReviewCountQueryPort;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class ReviewCountQueryAdapter implements ReviewCountQueryPort {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long countBySpaceId(long spaceId) {
        QReservationReviewJpaEntity rr = QReservationReviewJpaEntity.reservationReviewJpaEntity;
        QReservationJpaEntity r = QReservationJpaEntity.reservationJpaEntity;
        QSpaceJpaEntity s =  QSpaceJpaEntity.spaceJpaEntity;

        Long count = jpaQueryFactory
                .select(rr.count())
                .from(rr)
                .join(rr.reservation, r)
                .join(r.space, s)
                .where(s.spaceId.eq(spaceId))
                .fetchOne();

        return count != null ? count : 0;
    }

    @Override
    public Map<Long, Long> countBySpaceIds(Collection<Long> spaceIds) {
        if (spaceIds == null || spaceIds.isEmpty()) {
            return Collections.emptyMap();
        }

        QReservationReviewJpaEntity rr = QReservationReviewJpaEntity.reservationReviewJpaEntity;
        QReservationJpaEntity r = QReservationJpaEntity.reservationJpaEntity;
        QSpaceJpaEntity s =  QSpaceJpaEntity.spaceJpaEntity;

        List<Tuple> rows = jpaQueryFactory
                .select(s.spaceId, rr.count())
                .from(rr)
                .join(rr.reservation, r)
                .join(r.space, s)
                .where(s.spaceId.in(spaceIds))
                .groupBy(s.spaceId)
                .fetch();

        Map<Long, Long> map = new HashMap<>();

        for (Tuple t : rows) {
            map.put(t.get(s.spaceId), t.get(rr.count()));
        }

        for (Long id : spaceIds) {
            map.putIfAbsent(id, 0L);
        }

        return map;
    }
}
