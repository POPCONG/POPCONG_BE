package popcong.app.application.chat.port.in;

import popcong.app.application.chat.dto.response.CreateChatResponseDto;
import popcong.app.domain.chat.model.Chat;

public interface ChatRoomUseCase {
    CreateChatResponseDto createChat(Long userId, Long spaceId);
}
