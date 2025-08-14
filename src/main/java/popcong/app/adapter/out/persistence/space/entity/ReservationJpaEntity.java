package popcong.app.adapter.out.persistence.space.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import popcong.app.adapter.out.persistence.user.entity.UserJpaEntity;
import popcong.app.domain.space.model.ReservationStatus;
import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVATION")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservationId")
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private UserJpaEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spaceId", nullable = false)
    private SpaceJpaEntity space;

    @Enumerated(EnumType.STRING)
    @Column(name = "reservationStatus", nullable = false)
    private ReservationStatus reservationStatus;

    @Column(name = "startDate", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "endDate", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "deposit", nullable = false)
    private Integer deposit;

    @Column(name = "totalRentalFee", nullable = false)
    private Integer totalRentalFee;
}