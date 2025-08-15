package popcong.app.application.user.dto.response;

import popcong.app.application.space.dto.response.MyPopupItemResponseDto;
import java.util.List;

public record HomeUserResponseDto(
        UserInfoResponseDto userInfo,
        List<MyPopupItemResponseDto> myPopups
) {
    public static HomeUserResponseDto from(UserResponseDto user, List<MyPopupItemResponseDto> popups) {
        return new HomeUserResponseDto(
                UserInfoResponseDto.from(user),
                popups
        );
    }
}