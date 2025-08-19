package popcong.app.application.chat.dto.response;

import popcong.app.domain.user.model.UserRole;

import java.util.List;

public record ChatListResponseDto(
        UserRole userRole,
        List<ChatListItemDto> chatRoomList
) {
}
