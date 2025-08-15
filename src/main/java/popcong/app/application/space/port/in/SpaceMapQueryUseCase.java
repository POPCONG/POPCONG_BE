package popcong.app.application.space.port.in;

import popcong.app.domain.space.model.Space;
import popcong.app.domain.space.model.SpaceSortType;

import java.util.List;
import java.util.Map;

public interface SpaceMapQueryUseCase {
    List<Space> getSpaceMarkersInDisplayWithFilters(
            Double nwLat,
            Double nwLng,
            Double seLat,
            Double seLng,
            Integer minAmount,
            Integer maxAmount,
            Integer floor,
            Double rating,
            SpaceSortType sortType
    );

    Map<Long, Long> getReviewCountsFor(List<Space> spaces);
}
