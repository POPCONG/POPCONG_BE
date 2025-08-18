package popcong.app.adapter.out.persistence.space.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import popcong.app.adapter.out.persistence.space.entity.QPopupJpaEntity;
import popcong.app.adapter.out.persistence.space.entity.QReservationJpaEntity;
import popcong.app.adapter.out.persistence.space.entity.QSpaceJpaEntity;
import popcong.app.application.space.dto.response.NearbyPopupInfoDto;
import popcong.app.application.space.dto.response.NearbyPopupsDto;
import popcong.app.application.space.port.out.PopupQueryPort;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class PopupQueryAdapter implements PopupQueryPort {

    private final JPAQueryFactory jpaQueryFactory;

    // 근처 팝업 불러오기
    @Override
    public NearbyPopupsDto findNearbyPopups(double latitude, double longitude, double radiusKm, int limit) {

        QPopupJpaEntity popup = QPopupJpaEntity.popupJpaEntity;
        QReservationJpaEntity reservation = QReservationJpaEntity.reservationJpaEntity;
        QSpaceJpaEntity space = QSpaceJpaEntity.spaceJpaEntity;

        // 바운더리 결정
        double deltaLat = radiusKm / 111.0; // 위도 1도 ≈ 111km
        double deltaLng = radiusKm / (111.0 * Math.cos(Math.toRadians(latitude + deltaLat)));

        NumberTemplate<Double> distanceKm = com.querydsl.core.types.dsl.Expressions.numberTemplate(
                Double.class,
                "6371 * acos(least(greatest((cos(radians({0})) * cos(radians({1})) * cos(radians({2}) - radians({3})) + sin(radians({0})) * sin(radians({1}))), -1), 1))",
                latitude,
                space.latitude,
                space.longitude,
                longitude
        );

        List<NearbyPopupInfoDto> nearbyPopups = jpaQueryFactory
                .select(Projections.constructor(
                        NearbyPopupInfoDto.class,
                        popup.popupId,
                        popup.name,
                        popup.address,
                        popup.startDate,
                        popup.endDate
                ))
                .from(popup)
                .join(popup.reservation, reservation)
                .join(reservation.space, space)
                .where(
                        space.latitude.between(latitude - deltaLat, latitude + deltaLat),
                        space.longitude.between(longitude - deltaLng, longitude + deltaLng),
                        distanceKm.loe(radiusKm)
                )
                .orderBy(distanceKm.asc(), popup.startDate.asc())
                .limit(limit)
                .fetch();

        return new NearbyPopupsDto(nearbyPopups);
    }
}
