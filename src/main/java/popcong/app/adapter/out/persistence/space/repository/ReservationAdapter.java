package popcong.app.adapter.out.persistence.space.repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import popcong.app.adapter.out.persistence.space.entity.ReservationJpaEntity;
import popcong.app.adapter.out.persistence.space.entity.SpaceJpaEntity;
import popcong.app.adapter.out.persistence.user.entity.UserJpaEntity;
import popcong.app.application.space.port.out.ReservationPort;
import popcong.app.domain.space.model.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ReservationAdapter implements ReservationPort {

    private final EntityManager em;
    private final ReservationJpaRepository reservationRepo;

    @Transactional
    @Override
    public Long saveBooking(
            Long userId,
            Long spaceId,
            LocalDate startDate,
            LocalDate endDate,
            Integer deposit,
            Integer totalRentalFee
    ) {
        // 엔티티는 LocalDateTime이라면 변환 (00:00:00 기준 저장)
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end   = endDate.atStartOfDay();

        ReservationJpaEntity entity = ReservationJpaEntity.builder()
                .user(em.getReference(UserJpaEntity.class, userId))     // 조회 없이 FK 프록시
                .space(em.getReference(SpaceJpaEntity.class, spaceId))  // 조회 없이 FK 프록시
                .reservationStatus(ReservationStatus.BOOKING)
                .startDate(start)
                .endDate(end)
                .deposit(deposit)
                .totalRentalFee(totalRentalFee)
                .build();

        return reservationRepo.save(entity).getReservationId();
    }
}