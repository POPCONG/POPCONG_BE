package popcong.app.application.user.port.in;

import popcong.app.application.user.dto.response.HomeUserResponseDto;

public interface GetHomeInfoUseCase {
    HomeUserResponseDto getMyHomeInfo(Long userId, int limit, int offset);
}
