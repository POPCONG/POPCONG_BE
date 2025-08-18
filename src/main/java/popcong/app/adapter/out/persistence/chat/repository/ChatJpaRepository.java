package popcong.app.adapter.out.persistence.chat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import popcong.app.adapter.out.persistence.chat.entity.ChatJpaEntity;

import java.util.Optional;

public interface ChatJpaRepository extends JpaRepository<ChatJpaEntity, Long> {
    Optional<ChatJpaEntity> findByGuest_UserIdAndHost_UserIdAndSpace_SpaceId(Long guestId, Long hostId, Long spaceId);

    @EntityGraph(attributePaths = {"guest", "host"})
    Page<ChatJpaEntity> findByGuest_UserIdOrHost_UserIdOrderByLastMessageAtDesc(
            Long guestId, Long hostId, Pageable pageable
    );
}