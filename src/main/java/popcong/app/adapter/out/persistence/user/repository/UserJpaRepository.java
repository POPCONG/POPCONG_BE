package popcong.app.adapter.out.persistence.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import popcong.app.adapter.out.persistence.user.entity.UserJpaEntity;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    Optional<UserJpaEntity> findByEmail(String email);
}
