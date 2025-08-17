package popcong.app.adapter.out.persistence.space.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import popcong.app.adapter.out.persistence.space.entity.SpaceJpaEntity;

public interface SpaceJpaRepository extends JpaRepository<SpaceJpaEntity, Long>, JpaSpecificationExecutor<SpaceJpaEntity> {
}
