package popcong.app.application.space.port.out;

import popcong.app.application.space.dto.response.SpaceDetailDto;
import popcong.app.domain.space.model.Space;

import java.util.List;

public interface SpaceQueryPort {
  
    SpaceDetailDto findDetail(Long spaceId);

    List<Space> findPopularSpaceTop20();
}
