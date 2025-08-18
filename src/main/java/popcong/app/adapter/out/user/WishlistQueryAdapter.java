package popcong.app.adapter.out.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import popcong.app.adapter.out.persistence.user.repository.WishlistJpaRepository;
import popcong.app.application.user.dto.response.MyWishItemDto;
import popcong.app.application.user.port.out.WishListQueryPort;

import java.util.List;

@Component
@RequiredArgsConstructor
//목록 조회 포트 구현
public class WishlistQueryAdapter implements WishListQueryPort {


    private final WishlistJpaRepository wishlistJpaRepository;

    @Override
    public List<MyWishItemDto> findMyWishlist(Long userId) {
        return wishlistJpaRepository.findMyWishlist(userId);
    }

}
