package popcong.app.application.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import popcong.app.adapter.out.persistence.chat.entity.ChatJpaEntity;
import popcong.app.adapter.out.persistence.chat.repository.ChatJpaRepository;
import popcong.app.application.chat.port.in.SendMessageUseCase;
import popcong.app.application.chat.port.out.MessagePort;
import popcong.app.application.chat.port.out.MessagePublishPort;
import popcong.app.application.chat.dto.response.SendMessageResponseDto;
import popcong.app.domain.chat.model.Message;
import popcong.app.domain.chat.model.OutgoingMessage;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.ChatErrorCode;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SendMessageService implements SendMessageUseCase {

    private final ChatJpaRepository chatJpaRepository;
    private final MessagePort messagePort;
    private final MessagePublishPort messagePublishPort;


    @Override
    public SendMessageResponseDto send(Long chatId, Long senderId, String message) {
        if (message == null || message.isEmpty()) {
            throw new BusinessException(ChatErrorCode.EMPTY_MESSAGE);
        }

        ChatJpaEntity chat = chatJpaRepository.findById(chatId)
                .orElseThrow(() -> new BusinessException(ChatErrorCode.CHAT_NOT_FOUND));

        Long guestId = chat.getGuest().getUserId();
        Long hostId = chat.getHost().getUserId();

        if (!senderId.equals(guestId) && !senderId.equals(hostId)) {
            throw new BusinessException(ChatErrorCode.IS_NOT_PARTICIPANT);
        }

        LocalDateTime now = LocalDateTime.now();

        Message saved = messagePort.save(
                new Message(null, chatId, senderId, message, now)
        );

        messagePublishPort.publish(new OutgoingMessage(saved.messageId(), chatId, senderId, message, saved.createdAt()));

        return new SendMessageResponseDto(saved.messageId(), saved.createdAt());
    }
}
