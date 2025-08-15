package popcong.app.application.space.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import popcong.app.application.space.port.in.SpaceMapQueryUseCase;
import popcong.app.application.space.port.out.SpaceMapQueryPort;
import popcong.app.domain.space.model.Space;
import popcong.app.domain.space.model.SpaceSortType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpaceMapQueryService implements SpaceMapQueryUseCase {

    private final SpaceMapQueryPort spaceMapQueryPort;

    @Override
    public List<Space> getSpaceMarkersInDisplayWithFilters(
            Double nwLat,
            Double nwLng,
            Double seLat,
            Double seLng,
            Integer minAmount,
            Integer maxAmount,
            Integer floor,
            Double rating,
            SpaceSortType sortType
    ) {
        return spaceMapQueryPort.findSpacesInDisplayWithFilters(
                nwLat,
                nwLng,
                seLat,
                seLng,
                minAmount,
                maxAmount,
                floor,
                rating,
                sortType
        );
    }
}
