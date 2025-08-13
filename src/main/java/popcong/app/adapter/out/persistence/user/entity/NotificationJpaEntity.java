package popcong.app.adapter.out.persistence.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import popcong.app.adapter.out.persistence.space.entity.SpaceJpaEntity;
import popcong.app.adapter.out.persistence.user.entity.UserJpaEntity;
import popcong.app.domain.user.model.NotificationType;

import java.time.LocalDateTime;

@Entity
@Table(name = "NOTIFICATION")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class NotificationJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificationId", nullable = false)
    private Long notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private UserJpaEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spaceId", nullable = false)
    private SpaceJpaEntity space;

    @Enumerated(EnumType.STRING)
    @Column(name = "notificationType", nullable = false)
    private NotificationType notificationType;

    @Column(name = "message", nullable = false, length = 225)
    private String message;

    @Column(name = "url", nullable = false, length = 225)
    private String url;

    @Builder.Default
    @Column(name = "isRead", nullable = false)
    private Boolean isRead = false;

    @Builder.Default
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
