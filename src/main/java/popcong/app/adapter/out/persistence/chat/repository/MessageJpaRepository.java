package popcong.app.adapter.out.persistence.chat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import popcong.app.adapter.out.persistence.chat.entity.MessageJpaEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MessageJpaRepository extends JpaRepository<MessageJpaEntity, Long> {

    Page<MessageJpaEntity> findByChat_ChatId(Long chatChatId, Pageable pageable);
//    Page<MessageJpaEntity> findByChat_ChatIdOrderByCreatedAtAsc(Long chatId, Pageable pageable);

    // 안읽음 메시지 계산
    long countByChat_ChatIdAndSender_UserIdAndCreatedAtAfter(Long chatId, Long senderId, LocalDateTime createdAt);

    Optional<MessageJpaEntity> findTopByChat_ChatIdOrderByCreatedAtDesc(Long chatId);
}
