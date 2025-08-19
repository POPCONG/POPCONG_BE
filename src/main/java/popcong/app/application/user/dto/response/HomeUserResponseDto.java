package popcong.app.application.user.dto.response;

import java.util.List;

public record HomeUserResponseDto(
        UserInfoResponseDto userInfo,
        List<MyReservationItemDto> myReservations
) {
    public static HomeUserResponseDto from(UserResponseDto user, List<MyReservationItemDto> reservations) {
        return new HomeUserResponseDto(
                UserInfoResponseDto.from(user),
                reservations
        );
    }
}