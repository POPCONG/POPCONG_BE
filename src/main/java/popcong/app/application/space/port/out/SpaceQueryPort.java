package popcong.app.application.space.port.out;

import popcong.app.application.space.dto.response.SpaceDetailDto;

public interface SpaceQueryPort {
    SpaceDetailDto findDetail(Long spaceId);
}
