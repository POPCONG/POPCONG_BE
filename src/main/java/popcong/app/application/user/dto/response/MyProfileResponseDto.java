package popcong.app.application.user.dto.response;

import popcong.app.domain.user.model.User;

public record MyProfileResponseDto(
        Long userId,
        String name,
        String introduce,
        String profileImageUrl,
        String userRole
) {
    public static MyProfileResponseDto from(User user) {
        return new MyProfileResponseDto(
                user.userId(),
                user.name(),
                user.introduction(),
                user.profileImageUrl(),
                user.userRole().name()
        );
    }
}
