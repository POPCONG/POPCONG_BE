package popcong.app.application.chat.port.out;

import popcong.app.application.chat.dto.response.CreateChatResponseDto;
import popcong.app.domain.chat.model.Chat;
import popcong.app.domain.space.model.Space;
import popcong.app.domain.user.model.User;

import java.util.Optional;

public interface ChatRoomPort {

    Chat save(Chat chat);

    Optional<Long> findHostIdBySpaceId(Long spaceId);

    Optional<Chat> findExistingChat(Long guestId, Long hostId, Long roomId);

    Optional<Space> findSpaceById(Long spaceId);

    Optional<User> findUserById(Long userId);
}