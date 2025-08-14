package popcong.app.domain.space.model;

import java.time.LocalDateTime;

public record Space(
        Long spaceId,
        Long userId,
        String spaceName,
        SpaceType spaceType,
        SpaceApplicationType spaceApplicationType,
        PopupType spaceRentalType,
        Integer imageCount,
        String description,
        Integer deposit,
        Integer rentalFee,
        Integer floor,
        Double area,
        Double rating,
        String address,
        String location,
        Double latitude,
        Double longitude,
        Status status,
        Integer views,
        LocalDateTime createdAt
) {
}
