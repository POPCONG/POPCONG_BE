package popcong.app.adapter.out.persistence.space.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import popcong.app.application.image.port.in.ImageQueryUseCase;
import popcong.app.application.space.dto.response.MarkerComponentDto;
import popcong.app.domain.image.model.ImageableType;
import popcong.app.domain.space.model.Space;

@Component
@RequiredArgsConstructor
public class SpaceMarkerMapper {

    private final ImageQueryUseCase imageQueryUseCase;

    public MarkerComponentDto toMarkerDto(Space space, Long straightDistance, int reviewCount) {

        String coverImageUrl = imageQueryUseCase.getCoverImageUrl(ImageableType.SPACE, space.spaceId());

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
                straightDistance,
                space.maxPeriod()
        );
    }
}
