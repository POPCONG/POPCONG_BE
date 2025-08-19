package popcong.app.application.space.port.in;

import popcong.app.application.space.dto.response.HomeSpaceListResponseDto;
import popcong.app.application.space.dto.response.PopularSpacesDto;

public interface SpaceQueryUseCase {
    PopularSpacesDto findPopularSpaceTop20(Long userId);

    HomeSpaceListResponseDto loadHomeSpaceList(Long userId, double latitude, double longitude);
}
