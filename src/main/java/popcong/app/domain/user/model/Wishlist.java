package popcong.app.domain.user.model;

import java.time.LocalDateTime;

public record Wishlist(
        Long wishlistId,
        Long userId,
        Long spaceId,
        LocalDateTime createdAt //위시생성일
) {
}
