package popcong.app.adapter.out.persistence.space.mapper;

import org.springframework.stereotype.Component;
import popcong.app.adapter.out.persistence.space.entity.SpaceJpaEntity;
import popcong.app.domain.space.model.Space;

@Component
public class SpaceMapper {

    public Space toDomain(SpaceJpaEntity entity) {
        return new Space(
                entity.getSpaceId(),
                entity.getUser().getUserId(),
                entity.getSpaceName(),
                entity.getSpaceType(),
                entity.getSpaceApplicationType(),
                entity.getSpaceRentalType(),
                entity.getImageCount(),
                entity.getDescription(),
                entity.getDeposit(),
                entity.getRentalFee(),
                entity.getFloor(),
                entity.getArea(),
                entity.getRating(),
                entity.getAddress(),
                entity.getLocation(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getStatus(),
                entity.getViews()
        );
    }
}
