package popcong.app.application.chat.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import popcong.app.domain.chat.model.Message;

import java.time.LocalDateTime;

public interface MessagePort {

    // 메시지 저장
    Message save(Message message);

    // 채팅방 메시지 페이징으로 조회
    Page<Message> findByChat(Long chatId, Pageable pageable);

    // 상대가 보낸 안읽음 메시지 조회
    long countUnreadFromOpponent(Long chatId, Long opponentId, LocalDateTime after);
}
