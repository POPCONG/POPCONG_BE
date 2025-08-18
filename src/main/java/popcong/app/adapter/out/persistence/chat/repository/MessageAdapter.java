package popcong.app.adapter.out.persistence.chat.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import popcong.app.adapter.out.persistence.chat.entity.ChatJpaEntity;
import popcong.app.adapter.out.persistence.chat.entity.MessageJpaEntity;
import popcong.app.adapter.out.persistence.chat.mapper.MessageMapper;
import popcong.app.adapter.out.persistence.user.entity.UserJpaEntity;
import popcong.app.application.chat.port.out.MessagePort;
import popcong.app.domain.chat.model.Message;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
@Transactional
public class MessageAdapter implements MessagePort {

    private final MessageJpaRepository messageJpaRepository;
    private final ChatJpaRepository chatJpaRepository;
    private final EntityManager entityManager;
    private final MessageMapper messageMapper;

    // 메시지 저장
    @Override
    public Message save(Message message) {

        ChatJpaEntity chatReference = entityManager.getReference(ChatJpaEntity.class, message.chatId());
        UserJpaEntity senderReference = entityManager.getReference(UserJpaEntity.class, message.senderId());

        MessageJpaEntity entity = MessageJpaEntity.builder()
                .chat(chatReference)
                .sender(senderReference)
                .content(message.content())
                .createdAt(LocalDateTime.now())
                .build();

        MessageJpaEntity saved = messageJpaRepository.save(entity);

        return messageMapper.toDomain(saved);
    }

    // 메시지 내역 조회
    @Override
    public Page<Message> findByChat(Long chatId, Pageable pageable) {
        return messageJpaRepository.findByChat_ChatIdOrderByCreatedAtAsc(chatId, pageable)
                .map(messageMapper::toDomain);
    }

    @Override
    public long countUnreadFromOpponent(Long chatId, Long opponentId, LocalDateTime after) {
        return messageJpaRepository.countByChat_ChatIdAndSender_UserIdAndCreatedAtAfter(chatId, opponentId, after);
    }
}
