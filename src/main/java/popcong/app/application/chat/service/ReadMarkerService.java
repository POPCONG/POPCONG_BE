package popcong.app.application.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import popcong.app.application.chat.dto.response.ChatReadPointers;
import popcong.app.application.chat.port.in.ReadMarkerUseCase;
import popcong.app.application.chat.port.out.ChatReadPointerPort;
import popcong.app.application.chat.port.out.MessagePort;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.ChatErrorCode;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReadMarkerService implements ReadMarkerUseCase {

    private final ChatReadPointerPort chatReadPointerPort;
    private final MessagePort messagePort;


    @Override
    public void markAsRead(Long chatId, Long userId, LocalDateTime lastSeenAt) {
        LocalDateTime at = lastSeenAt != null ? lastSeenAt : LocalDateTime.now();
        chatReadPointerPort.updatePointer(chatId, userId, at);
    }

    @Override
    public long countMyUnread(Long chatId, Long userId) {
        ChatReadPointers pointers = chatReadPointerPort.findPointers(chatId)
                .orElseThrow(() -> new BusinessException(ChatErrorCode.CHAT_NOT_FOUND));

        boolean amGuest = userId.equals(pointers.guestId());
        boolean amHost = userId.equals(pointers.hostId());

        if (!amGuest && !amHost) {
            throw new BusinessException(ChatErrorCode.IS_NOT_PARTICIPANT);
        }

        Long opponentId = amGuest ? pointers.hostId() : pointers.guestId();
        LocalDateTime myLastReadAt = amGuest ? pointers.guestLastReadAt() : pointers.hostLastReadAt();
        LocalDateTime after = myLastReadAt != null ? myLastReadAt : LocalDateTime.MIN;

        return messagePort.countUnreadFromOpponent(chatId, opponentId, after);
    }
}
