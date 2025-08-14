package popcong.app.adapter.out.persistence.space.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import popcong.app.adapter.out.persistence.space.entity.ReservationJpaEntity;
import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVATION_REVIEW")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationReviewJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservationReviewId", nullable = false)
    private Long reservationReviewId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservationId", nullable = false)
    private ReservationJpaEntity reservation;

    @Builder.Default
    @Column(name = "rating", nullable = false)
    private Double rating =0.0;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
}