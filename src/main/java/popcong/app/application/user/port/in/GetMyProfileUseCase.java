package popcong.app.application.user.port.in;

import popcong.app.application.user.dto.response.MyProfileResponseDto;

public interface GetMyProfileUseCase {
    MyProfileResponseDto get(Long userId);
}
