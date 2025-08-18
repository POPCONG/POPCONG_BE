package popcong.app.application.user.dto.response;


public record WishResponseDto(
        Long userId,
        Long spaceId,
        boolean isLiked,
        String createdAt // unlike 응답에서는 null
) {
}