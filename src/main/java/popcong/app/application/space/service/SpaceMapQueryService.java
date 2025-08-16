package popcong.app.application.space.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import popcong.app.adapter.out.persistence.space.mapper.SpaceMarkerMapper;
import popcong.app.application.image.port.in.GeoDistanceUseCase;
import popcong.app.application.space.dto.response.MarkerComponentDto;
import popcong.app.application.space.port.in.SpaceMapQueryUseCase;
import popcong.app.application.space.port.out.ReviewCountQueryPort;
import popcong.app.application.space.port.out.SpaceMapQueryPort;
import popcong.app.domain.space.model.Space;
import popcong.app.domain.space.model.SpaceSortType;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SpaceMapQueryService implements SpaceMapQueryUseCase {

    private final SpaceMapQueryPort spaceMapQueryPort;
    private final ReviewCountQueryPort reviewCountQueryPort;
    private final GeoDistanceUseCase geoDistanceUseCase;   // ← 서비스에서만 사용
    private final SpaceMarkerMapper spaceMarkerMapper;

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

    // 집계 후 Mapper에 전달하기 위함
    public Map<Long, Long> getReviewCountsFor(List<Space> spaces) {
        var ids = spaces.stream()
                .map(Space::spaceId)
                .toList();

        return reviewCountQueryPort.countBySpaceIds(ids);
    }

    @Override
    public List<MarkerComponentDto> getMarkerInDisplayWithFilters(
            Double userLat,
            Double userLng,
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
        // 공간 목록 조회
        List<Space> spaces = getSpaceMarkersInDisplayWithFilters(
                nwLat, nwLng, seLat, seLng,
                minAmount, maxAmount, floor, rating, sortType
        );

        if (spaces.isEmpty()) return List.of();

        // 리뷰 집계
        Map<Long, Long> reviewCounts = getReviewCountsFor(spaces);
        if (spaces.isEmpty()) return List.of();

        // 거리 계산
        Map<Long, Long> distances = new HashMap<>();
        boolean hasUserLocation = (userLat != null && userLng != null);
        for (Space s : spaces) {
            Long d = hasUserLocation
                    ? geoDistanceUseCase.getStraightDistance(userLat, userLng, s.latitude(), s.longitude())
                    : null; // 위치 없으면 null
            distances.put(s.spaceId(), d);
        }

        Map<Long, Long> distancesForSort = new HashMap<>();
        for (Space s : spaces) {
            Long d = distances.get(s.spaceId());
            distancesForSort.put(s.spaceId(), (d != null) ? d : Long.MAX_VALUE);
        }

        // 정렬
        Comparator<Space> byIdAsc       = Comparator.comparing(Space::spaceId);
        Comparator<Space> byViewsDesc    = Comparator.comparing(Space::views).reversed();
        Comparator<Space> byReviewsDesc  = Comparator.<Space, Long>comparing(s -> reviewCounts.getOrDefault(s.spaceId(), 0L)).reversed();
        Comparator<Space> byDistanceAsc  = Comparator.comparing(s -> distancesForSort.getOrDefault(s.spaceId(), Long.MAX_VALUE));

        List<Space> sorted = new ArrayList<>(spaces);

        SpaceSortType st = (sortType != null) ? sortType : SpaceSortType.MOST_POPULAR;
        switch (st) {
            case MOST_POPULAR -> sorted.sort(byViewsDesc.thenComparing(byIdAsc));

            case NEAREST -> {
                if (!hasUserLocation) {
                    sorted.sort(byViewsDesc.thenComparing(byIdAsc));
                } else {
                    sorted.sort(byDistanceAsc.thenComparing(byIdAsc));
                }
            }

            case MOST_REVIEWS -> sorted.sort(byReviewsDesc.thenComparing(byDistanceAsc).thenComparing(byIdAsc));
        }

        return sorted.stream()
                .map(s -> spaceMarkerMapper.toMarkerDto(
                        s,
                        distances.get(s.spaceId()),
                        reviewCounts.getOrDefault(s.spaceId(), 0L).intValue()
                ))
                .toList();
    }
}
