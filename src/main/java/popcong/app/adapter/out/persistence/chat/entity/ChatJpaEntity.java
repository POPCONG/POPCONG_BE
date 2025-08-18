package popcong.app.adapter.out.persistence.chat.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import popcong.app.adapter.out.persistence.user.entity.UserJpaEntity;
import popcong.app.adapter.out.persistence.space.entity.SpaceJpaEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "CHAT")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatId", nullable = false)
    private Long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guestId", nullable = false)
    private UserJpaEntity guest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostId", nullable = false)
    private UserJpaEntity host;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spaceId", nullable = false)
    private SpaceJpaEntity space;

    @Builder.Default
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "lastMessageAt", nullable = false)
    private LocalDateTime lastMessageAt;

    @Column(name = "lastReadAt")
    private LocalDateTime lastReadAt;

    @Column(name = "guestLastReadAt")
    private LocalDateTime guestLastReadAt;

    @Column(name = "hostLastReadAt")
    private LocalDateTime hostLastReadAt;

    @Version
    private Long version;
    
    public void updateGuestLastReadAt(LocalDateTime at) {
        if (at == null) return;
        if (this.guestLastReadAt == null || this.guestLastReadAt.isBefore(at)) {
            this.guestLastReadAt = at;
        }
    }

    public void updateHostLastReadAt(LocalDateTime at) {
        if (at == null) return;
        if (this.hostLastReadAt == null || this.hostLastReadAt.isBefore(at)) {
            this.hostLastReadAt = at;
        }
    }
}
