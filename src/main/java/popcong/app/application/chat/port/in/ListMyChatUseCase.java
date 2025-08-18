package popcong.app.application.chat.port.in;

import org.springframework.data.domain.Pageable;
import popcong.app.application.chat.dto.response.ChatListResponseDto;

public interface ListMyChatUseCase {
    ChatListResponseDto listMyChat(Long userId, Pageable pageable);
}
