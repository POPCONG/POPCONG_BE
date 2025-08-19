package popcong.app.application.space.dto.response;

public record SpaceDetailDto(
        Long spaceId,
        String name,
        String address,
        Integer rentalFee,
        Integer deposit,
        Double rating,
        String coverImageUrl,
        boolean isWishlisted
) {
}
