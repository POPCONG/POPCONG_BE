package popcong.app.application.space.dto.response;

import java.util.List;

public record NearbyPopupsDto(
        List<NearbyPopupInfoDto> nearbyPopups
) {}