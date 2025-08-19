package popcong.app.adapter.out.persistence.chat.repository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import popcong.app.adapter.out.persistence.chat.entity.ChatJpaEntity;
import popcong.app.application.chat.dto.response.ChatReadPointers;
import popcong.app.application.chat.port.out.ChatReadPointerPort;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.ChatErrorCode;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class ChatReadPointerAdapter implements ChatReadPointerPort {

    private final ChatJpaRepository chatJpaRepository;

    @Override
    public Optional<ChatReadPointers> findPointers(Long chatId) {
        return chatJpaRepository.findById(chatId).map(chat ->
                new ChatReadPointers(
                        chat.getGuest().getUserId(),
                        chat.getHost().getUserId(),
                        chat.getGuestLastReadAt(),
                        chat.getHostLastReadAt()
                )
        );
    }

    // 읽음 상태 변경
    @Override
    public void updatePointer(Long chatId, Long userId, LocalDateTime at) {

        ChatJpaEntity chatJpaEntity = chatJpaRepository.findById(chatId)
                .orElseThrow(() -> new BusinessException(ChatErrorCode.CHAT_NOT_FOUND));

        Long guestId = chatJpaEntity.getGuest().getUserId();
        Long hostId = chatJpaEntity.getHost().getUserId();

        if (userId.equals(guestId)) {
            chatJpaEntity.updateGuestLastReadAt(at != null ? at : LocalDateTime.now());
        } else if (userId.equals(hostId)) {
            chatJpaEntity.updateHostLastReadAt(at != null ? at : LocalDateTime.now());
        } else {
            throw new BusinessException(ChatErrorCode.IS_NOT_PARTICIPANT);
        }
    }
}
