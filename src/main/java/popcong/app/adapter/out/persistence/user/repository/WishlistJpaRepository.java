// src/main/java/popcong/app/adapter/out/persistence/user/repository/WishlistJpaRepository.java
package popcong.app.adapter.out.persistence.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import popcong.app.adapter.out.persistence.user.entity.WishlistJpaEntity;

import java.util.Collection;
import java.util.List;

public interface WishlistJpaRepository extends JpaRepository<WishlistJpaEntity, Long>, WishlistQueryRepository {

    boolean existsByUser_UserIdAndSpace_SpaceId(Long userId, Long spaceId);
    void deleteByUser_UserIdAndSpace_SpaceId(Long userId, Long spaceId);
    
    List<WishlistJpaEntity> findAllByUser_UserIdOrderByCreatedAtDesc(Long userId);

    List<WishlistJpaEntity> findByUser_UserIdAndSpace_SpaceIdIn(Long userId, Collection<Long> spaceIds);
}