package popcong.app.application.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import popcong.app.application.chat.dto.response.ChatRoomInfoDto;
import popcong.app.application.chat.port.in.ChatRoomUseCase;
import popcong.app.application.chat.port.out.ChatRoomPort;
import popcong.app.domain.chat.model.Chat;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.ChatErrorCode;
import popcong.app.global.exception.error.SpaceErrorCode;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService implements ChatRoomUseCase {

    private final ChatRoomPort chatRoomPort;
    private final Clock clock;

    @Override
    public Chat createChat(Long userId, Long spaceId) {

        if (userId == null || spaceId == null) {
            throw new BusinessException(ChatErrorCode.INVALID_CHAT_ROOM);
        }

        Long hostId = chatRoomPort.findHostIdBySpaceId(spaceId)
                .orElseThrow(() -> new BusinessException(SpaceErrorCode.SPACE_NOT_FOUND));

        if (hostId.equals(userId)) {
            throw new BusinessException(ChatErrorCode.SELF_CHAT_NOT_ALLOWED);
        }

        Optional<Chat> existing = chatRoomPort.findExistingChat(userId, hostId, spaceId);
        if (existing.isPresent()) {
            ChatRoom
        }

        LocalDateTime now = LocalDateTime.now(clock);
        Chat newChat = new Chat(
                null, userId, hostId, spaceId, now, now, now
        );

        return chatRoomPort.save(newChat);
    }

    private Optional<ChatRoomInfoDto> findExistChatRoom(Long guestId, Long hostId, Long spaceId) {
        var existing = chatRoomPort.findExistingChat(guestId, hostId, spaceId);
        if (existing != null) {
            var details = chatRoomPort.loadChatRoomDetails(existing.get().chatId());
            var response =
        }
    }
}
