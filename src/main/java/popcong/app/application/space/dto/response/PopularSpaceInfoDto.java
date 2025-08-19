package popcong.app.application.space.dto.response;

import java.util.List;

public record PopularSpaceInfoDto(
   Long spaceId,
   String spaceName,
   List<String> imageUrl,
   String address,
   Double rating,
   Integer rentalFee,
   Integer deposit,
   String geoAdvantage,
   Boolean isWished,
   Long reviewCount,
   Integer maxPeriod
) {}
