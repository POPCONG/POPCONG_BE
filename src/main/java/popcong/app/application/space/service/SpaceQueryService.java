package popcong.app.application.space.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import popcong.app.application.image.port.out.ImageQueryPort;
import popcong.app.application.space.dto.response.HomeSpaceListResponseDto;
import popcong.app.application.space.dto.response.NearbyPopupsDto;
import popcong.app.application.space.dto.response.PopularSpaceInfoDto;
import popcong.app.application.space.dto.response.PopularSpacesDto;
import popcong.app.application.space.port.in.SpaceQueryUseCase;
import popcong.app.application.space.port.out.PopupQueryPort;
import popcong.app.application.space.port.out.ReviewCountQueryPort;
import popcong.app.application.space.port.out.SpaceQueryPort;
import popcong.app.application.user.port.out.WishListQueryPort;
import popcong.app.domain.image.model.Image;
import popcong.app.domain.image.model.ImageableType;
import popcong.app.domain.space.model.Space;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SpaceQueryService implements SpaceQueryUseCase {

    private final SpaceQueryPort spaceQueryPort;
    private final ImageQueryPort imageQueryPort;
    private final WishListQueryPort wishlistQueryPort;
    private final ReviewCountQueryPort reviewCountQueryPort;
    private final PopupQueryPort popupQueryPort;

    // 홈 매물 조회 부분 비지니스 로직
    @Override
    public HomeSpaceListResponseDto loadHomeSpaceList(Long userId, double latitude, double longitude) {
        PopularSpacesDto popularSpaces = findPopularSpaceTop20(userId);
        NearbyPopupsDto nearbyPopupsDto = popupQueryPort.findNearbyPopups(latitude, longitude, 20.0, 20);

        return new HomeSpaceListResponseDto(popularSpaces, nearbyPopupsDto);
    }

    // 인기 매물 20개 추출 - 조회수 기반
    @Override
    public PopularSpacesDto findPopularSpaceTop20(Long userId) {

        // 인기 매물 20개 데이터 반환
        List<Space> spaces = spaceQueryPort.findPopularSpaceTop20();

        // 빈 리스트 반환 (데이터 없는 경우)
        if (spaces.isEmpty()) {
            return new PopularSpacesDto(List.of());
        }

        // spaceId, isWished 조회
        List<Long> spaceIds = spaces.stream().map(Space::spaceId).toList();
        Set<Long> wishIds = (userId == null) ? Set.of() : wishlistQueryPort.findWishedSpaceIds(userId, spaceIds);
        Map<Long, Long> reviewCounts = reviewCountQueryPort.countBySpaceIds(spaceIds);

        // 이미지 Dto 매핑
        List<PopularSpaceInfoDto> popularSpaceInfoDtos = spaces.stream()
                .map(space -> {
                    List<String> imageUrls = imageQueryPort
                            .findSpaceImages(space.spaceId(), ImageableType.SPACE, 5)
                            .stream()
                            .map(Image::imageUrl)
                            .toList();

                    boolean isWished = wishIds.contains(space.spaceId());
                    Long reviewCount = reviewCounts.getOrDefault(space.spaceId(), 0L);

                    return findPopularSpaceInfo(space, imageUrls, isWished, reviewCount);
                })
                .toList();

        return new  PopularSpacesDto(popularSpaceInfoDtos);
    }






    private PopularSpaceInfoDto findPopularSpaceInfo(Space space, List<String> imageUrls, boolean isWished,  Long reviewCount) {
        return new PopularSpaceInfoDto(
                space.spaceId(),
                space.spaceName(),
                imageUrls,
                space.address(),
                space.rating(),
                space.rentalFee(),
                space.deposit(),
                space.geoAdvantage(),
                isWished,
                reviewCount
        );
    }
}
