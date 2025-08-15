package popcong.app.adapter.out.persistence.space.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import popcong.app.adapter.out.persistence.space.entity.QReservationJpaEntity;
import popcong.app.adapter.out.persistence.space.entity.QReservationReviewJpaEntity;
import popcong.app.adapter.out.persistence.space.entity.QSpaceJpaEntity;
import popcong.app.adapter.out.persistence.space.entity.SpaceJpaEntity;
import popcong.app.adapter.out.persistence.space.mapper.SpaceMapper;
import popcong.app.application.space.port.out.SpaceMapQueryPort;
import popcong.app.domain.space.model.Space;
import popcong.app.domain.space.model.SpaceSortType;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.SpaceErrorCode;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SpaceMapQueryAdapter implements SpaceMapQueryPort {

//    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;
    private final SpaceMapper spaceMapper;

    @Override
    public List<Space> findSpacesInDisplayWithFilters(
            Double nwLat,
            Double nwLng,
            Double seLat,
            Double seLng,
            Integer minAmount,
            Integer maxAmount,
            Integer floor,
            Double rating,
            SpaceSortType sortType
    ) {

        if (nwLat == null || nwLng == null || seLat == null || seLng == null) {
            throw new BusinessException(SpaceErrorCode.INVALID_BOUDING_BOX);
        }

        // 박스 정규화
        double minLat = Math.min(nwLat, seLat);
        double maxLat = Math.max(nwLat, seLat);
        double minLng = Math.min(nwLng, seLng);
        double maxLng = Math.max(nwLng, seLng);

        QSpaceJpaEntity s = QSpaceJpaEntity.spaceJpaEntity;
//        QReservationJpaEntity r = QReservationJpaEntity.reservationJpaEntity;
//        QReservationReviewJpaEntity rr = QReservationReviewJpaEntity.reservationReviewJpaEntity;

        BooleanExpression inBox = s.latitude.between(minLat, maxLat).and(s.longitude.between(minLng, maxLng));
        BooleanExpression feeMin = (minAmount != null) ? s.rentalFee.goe(minAmount) : null;
        BooleanExpression feeMax = (maxAmount != null) ? s.rentalFee.loe(maxAmount) : null;
        BooleanExpression floorEq = (floor != null) ? s.floor.eq(floor) : null;
        BooleanExpression ratingG = (rating != null) ? s.rating.goe(rating) : null;

        if (sortType == null) {
            sortType = SpaceSortType.MOST_POPULAR;
        }

        List<SpaceJpaEntity> entities = switch (sortType) {
            case MOST_POPULAR -> jpaQueryFactory.selectFrom(s)
                    .where(inBox, feeMin, feeMax, floorEq, ratingG)
                    .orderBy(s.views.desc(), s.spaceId.asc())
                    .fetch();
            case NEAREST, MOST_REVIEWS -> jpaQueryFactory
                    .selectFrom(s)
                    .where(inBox, feeMin, feeMax, floorEq, ratingG)
                    .orderBy(s.spaceId.asc())
                    .fetch();
        };

        return entities.stream().map(spaceMapper::toDomain).toList();
    }
}
