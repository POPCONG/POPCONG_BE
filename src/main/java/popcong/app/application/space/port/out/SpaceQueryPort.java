package popcong.app.application.space.port.out;

import popcong.app.domain.space.model.Space;

import java.util.List;

public interface SpaceQueryPort {

    List<Space> findPopularSpaceTop20();
}
