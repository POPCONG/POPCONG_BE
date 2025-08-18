package popcong.app.application.user.port.out;


import popcong.app.application.user.dto.response.MyWishItemDto;

import java.util.List;

//userId 기준 관심목록 조회
public interface WishListQueryPort {
    List<MyWishItemDto> findMyWishlist(Long userId);
}
