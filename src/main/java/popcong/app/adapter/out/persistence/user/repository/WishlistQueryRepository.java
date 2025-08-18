package popcong.app.adapter.out.persistence.user.repository;

import popcong.app.application.user.dto.response.MyWishItemDto;

import java.util.List;

public interface WishlistQueryRepository {
    List<MyWishItemDto> findMyWishlist(Long userId);
}
