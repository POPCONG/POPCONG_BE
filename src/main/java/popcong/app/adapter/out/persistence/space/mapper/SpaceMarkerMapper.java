package popcong.app.adapter.out.persistence.space.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import popcong.app.application.image.port.in.GeoDistanceUseCase;
import popcong.app.application.image.port.in.ImageQueryUseCase;
import popcong.app.application.space.dto.response.MarkerComponentDto;
import popcong.app.domain.image.model.ImageableType;
import popcong.app.domain.space.model.Space;

@Component
@RequiredArgsConstructor
public class SpaceMarkerMapper {

    private final ImageQueryUseCase imageQueryUseCase;
    private final GeoDistanceUseCase geoDistanceUseCase;

    public MarkerComponentDto toMarkerDto(Space space, Double userLat, Double userLng) {
        return toMarkerDto(space, userLat, userLng, 0);
    }

    public MarkerComponentDto toMarkerDto(Space space, Double userLat, Double userLng, int reviewCount) {

        String coverImageUrl = imageQueryUseCase.getCoverImageUrl(ImageableType.SPACE, space.spaceId());

        Long straightDistance = geoDistanceUseCase.getStraightDistance(
                userLat, userLng, space.latitude(), space.longitude()
        );

        return new MarkerComponentDto(
                space.spaceId(),
                space.latitude(),
                space.longitude(),
                space.spaceName(),
                coverImageUrl,
                space.rentalFee(),
                space.address(),
                space.floor(),
                space.rating(),
                reviewCount,
                straightDistance
        );
    }
}
