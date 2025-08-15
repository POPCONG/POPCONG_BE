package popcong.app.adapter.out.persistence.space.mapper;

import org.springframework.stereotype.Component;
import popcong.app.application.space.dto.response.MarkerComponentDto;
import popcong.app.domain.space.model.Space;

@Component
public class SpaceMarkerMapper {

    public MarkerComponentDto toMarkerDto(Space space) {
        return new MarkerComponentDto(
                space.spaceId(),
                space.latitude(),
                space.longitude(),
                space.spaceName(),
                null,
                space.rentalFee(),
                space.address(),
                space.floor(),
                space.rating(),
                0,
                null
        );
    }
}
