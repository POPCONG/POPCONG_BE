package popcong.app.application.chat.port.in;

import popcong.app.application.chat.dto.response.SendMessageResponseDto;

public interface SendMessageUseCase {
    SendMessageResponseDto send(Long chatId, Long senderId, String message);
}
