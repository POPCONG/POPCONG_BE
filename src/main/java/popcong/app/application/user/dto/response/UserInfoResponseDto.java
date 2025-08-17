package popcong.app.application.user.dto.response;

public record UserInfoResponseDto(
        String name
) {
    public static UserInfoResponseDto from(UserResponseDto user) {
        return new UserInfoResponseDto(user.name());
    }
}