package popcong.app.application.chat.dto.response;

public record PartnerInfoDto(
        Long userId,
        String name,
        String profileImageUrl
) {
}
