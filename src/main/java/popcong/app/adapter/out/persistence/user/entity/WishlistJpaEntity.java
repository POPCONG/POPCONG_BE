package popcong.app.adapter.out.persistence.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import popcong.app.adapter.out.persistence.space.entity.SpaceJpaEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "WISHLIST")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistJpaEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private UserJpaEntity user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spaceId", nullable = false)
    private SpaceJpaEntity space;


    @Builder.Default
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
