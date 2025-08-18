package popcong.app.adapter.out.persistence.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import popcong.app.application.user.dto.response.MyWishItemDto;
import popcong.app.application.user.port.out.WishListQueryPort;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
@Transactional
public class WishlistQueryAdapter implements WishListQueryPort {

    private final WishlistJpaRepository wishlistJpaRepository;

    @Override
    public Set<Long> findWishedSpaceIds(Long userId, Collection<Long> spaceIds) {
        if (userId == null || spaceIds == null || spaceIds.isEmpty()) {
            return Set.of();
        }

        return wishlistJpaRepository
                .findByUser_UserIdAndSpace_SpaceIdIn(userId, spaceIds).stream()
                .map(w -> w.getSpace().getSpaceId())
                .collect(java.util.stream.Collectors.toSet());
    }

    @Override
    public boolean existsByUserIdAndSpaceId(Long userId, Long spaceId) {
        if (userId == null || spaceId == null) return false;
        return wishlistJpaRepository.existsByUser_UserIdAndSpace_SpaceId(userId, spaceId);
    }

    @Override
    public List<MyWishItemDto> findMyWishlist(Long userId) {
        return wishlistJpaRepository.findMyWishlist(userId);
    }
}