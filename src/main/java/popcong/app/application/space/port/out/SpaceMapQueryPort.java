package popcong.app.application.space.port.out;

import popcong.app.domain.space.model.Space;
import popcong.app.domain.space.model.SpaceSortType;

import java.util.List;

// 지도 안의 마커 조건부 query 레포지토리
public interface SpaceMapQueryPort {
    List<Space> findSpacesInDisplayWithFilters(
            Double nwLat,
            Double nwLng,
            Double seLat,
            Double seLng,
            Integer minAmount,
            Integer maxAmount,
            Integer floor,
            Double rating,
            SpaceSortType sortType
//            Integer limit,
//            Integer offset
    );
}