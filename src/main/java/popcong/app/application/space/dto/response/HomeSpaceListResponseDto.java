package popcong.app.application.space.dto.response;

public record HomeSpaceListResponseDto(
        PopularSpacesDto popularSpacesDto,
        NearbyPopupsDto nearbyPopupsDto
) {}