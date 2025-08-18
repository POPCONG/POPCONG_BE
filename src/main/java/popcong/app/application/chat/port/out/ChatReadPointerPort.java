package popcong.app.application.chat.port.out;

import popcong.app.application.chat.dto.response.ChatReadPointers;

import java.time.LocalDateTime;
import java.util.Optional;

// 메시지 읽음 여부 관련 port
public interface ChatReadPointerPort {

    // 읽음 조회
    Optional<ChatReadPointers> findPointers(Long chatId);

    // userId에 따라 상대 읽음 여부 변경
    void updatePointer(Long chatId, Long userId, LocalDateTime at);
}
