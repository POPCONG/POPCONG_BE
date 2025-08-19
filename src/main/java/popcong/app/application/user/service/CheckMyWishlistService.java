package popcong.app.application.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import popcong.app.application.user.dto.response.MyWishItemDto;
import popcong.app.application.user.port.out.WishListQueryPort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckMyWishlistService {

    private final WishListQueryPort wishListQueryPort;

    public List<MyWishItemDto> excute(Long userId) {
        return wishListQueryPort.findMyWishlist(userId);
    }
}
