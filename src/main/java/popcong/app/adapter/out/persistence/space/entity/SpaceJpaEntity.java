package popcong.app.adapter.out.persistence.space.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import popcong.app.adapter.out.persistence.user.entity.UserJpaEntity;
import popcong.app.domain.space.model.PopupType;
import popcong.app.domain.space.model.SpaceApplicationType;
import popcong.app.domain.space.model.SpaceType;
import popcong.app.domain.space.model.Status;


@Entity
@Table(name = "SPACE")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spaceId")
    private Long spaceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private UserJpaEntity user;

    @Column(name = "spaceName", nullable = false, length = 50)
    private String spaceName;

    @Enumerated(EnumType.STRING)
    @Column(name = "spaceType", nullable = false)
    private SpaceType spaceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "spaceApplicationType", nullable = false)
    private SpaceApplicationType spaceApplicationType; // 공간 유형

    @Enumerated(EnumType.STRING)
    @Column(name = "spaceRentalType", nullable = false)
    private PopupType spaceRentalType;

    @Builder.Default
    @Column(name = "imageCount", nullable = false)
    private Integer imageCount = 0;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    @Column(name = "deposit", nullable = false)
    private Integer deposit = 0;

    @Builder.Default
    @Column(name = "rentalFee", nullable = false)
    private Integer rentalFee = 0;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "area", nullable = false)
    private Double area;

    @Builder.Default
    @Column(name = "rating", nullable = false)
    private Double rating = 0.0;

    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @Column(name = "location", length = 30)
    private String location;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "geoAdvantage", nullable = true, length = 30)
    private String geoAdvantage;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.AVAILABLE;

    @Builder.Default
    @Column(name = "views", nullable = false)
    private Integer views = 0;
}

