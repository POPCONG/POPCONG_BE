package popcong.app.adapter.out.persistence.space.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import popcong.app.domain.space.model.PopupStatus;
import popcong.app.domain.space.model.PopupType;

import java.time.LocalDateTime;

@Entity
@Table(name = "POPUP")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PopupJpaEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "popupId", nullable = false)
    private Long popupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservationId", nullable = false)
    private ReservationJpaEntity reservation;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "popupStatus", nullable = false)
    private PopupStatus popupStatus;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "popupType", nullable = false)
    private PopupType popupType;

    @Builder.Default
    @Column(name = "price", nullable = false)
    private Integer price = 0;

    @Column(name = "startDate", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "endDate", nullable = false)
    private LocalDateTime endDate;

    @Builder.Default
    @Column(name = "views", nullable = false)
    private Integer views = 0;
}

