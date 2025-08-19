package popcong.app.adapter.out.persistence.space.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import popcong.app.adapter.out.persistence.space.entity.QPopupJpaEntity;
import popcong.app.adapter.out.persistence.space.entity.QReservationJpaEntity;
import popcong.app.adapter.out.persistence.space.entity.QSpaceJpaEntity;
import popcong.app.application.user.dto.response.MyReservationItemDto;
import popcong.app.application.user.port.out.ReservationQueryPort;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationQueryAdapter implements ReservationQueryPort {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MyReservationItemDto> findMyReservations(Long userId) {
        QReservationJpaEntity r = QReservationJpaEntity.reservationJpaEntity;
        QPopupJpaEntity p = QPopupJpaEntity.popupJpaEntity;
        QSpaceJpaEntity s = QSpaceJpaEntity.spaceJpaEntity;

        return queryFactory
                .select(Projections.constructor(MyReservationItemDto.class,
                        p.popupStatus,
                        p.name,
                        s.address,
                        p.startDate,
                        p.endDate
                ))
                .from(p)
                .join(p.reservation, r)
                .join(r.space, s)
                .where(r.user.userId.eq(userId))
                .orderBy(p.startDate.desc())
                .fetch();
    }
}
