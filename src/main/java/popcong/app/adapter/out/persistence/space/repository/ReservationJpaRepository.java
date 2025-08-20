package popcong.app.adapter.out.persistence.space.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import popcong.app.adapter.out.persistence.space.entity.ReservationJpaEntity;

public interface ReservationJpaRepository extends JpaRepository<ReservationJpaEntity, Long> {
}