package popcong.app.application.user.port.out;

import java.util.Collection;
import java.util.Set;

public interface WishlistQueryPort {
    Set<Long> findWishedSpaceIds(Long userId, Collection<Long> spaceIds);

    boolean existsByUserIdAndSpaceId(Long userId, Long spaceId);
}
