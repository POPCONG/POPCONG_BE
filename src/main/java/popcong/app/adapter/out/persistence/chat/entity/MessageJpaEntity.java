package popcong.app.adapter.out.persistence.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import popcong.app.adapter.out.persistence.user.entity.UserJpaEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "MESSAGE")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatId", nullable = false)
    private ChatJpaEntity chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senderId", nullable = false)
    private UserJpaEntity sender;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
