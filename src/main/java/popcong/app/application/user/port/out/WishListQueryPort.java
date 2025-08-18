package popcong.app.application.user.port.out;

import java.util.Collection;
import java.util.List;
import java.util.Set;

//userId 기준 관심목록 조회
public interface WishListQueryPort {
    List<MyWishItemDto> findMyWishlist(Long userId);

    Set<Long> findWishedSpaceIds(Long userId, Collection<Long> spaceIds);

    boolean existsByUserIdAndSpaceId(Long userId, Long spaceId);
}
