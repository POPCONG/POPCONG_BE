package popcong.app.application.user.dto.response;

public record MyWishItemDto(
        Long spaceId,
        String coverImage,
        Integer rentalFee,
        Integer deposit,
        String address,
        Double rating,
        boolean isWishlisted
) {
}
