package popcong.app.application.space.dto.response;

import popcong.app.domain.space.model.*;

public record SpaceDetailResponseDto(
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
        String location, //상세 주소
        Double latitude,
        Double longitude,
        String geoAdvantage, //인근 전철역
        Integer maxPeriod,
        Status status,
        Integer views,
        Boolean isWished,
        String coverImageUrl
) {
}
