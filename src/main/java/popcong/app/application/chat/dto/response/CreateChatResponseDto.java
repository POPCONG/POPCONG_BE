package popcong.app.application.chat.dto.response;

public record CreateChatResponseDto(
        ChatRoomInfoDto chatRoomInfo,
        ParticipantInfoDto guestInfo,
        ParticipantInfoDto hostInfo
) {
}
