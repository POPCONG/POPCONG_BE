package popcong.app.adapter.out.persistence.space.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import popcong.app.adapter.out.persistence.space.entity.PopupJpaEntity;

import java.util.List;

public interface PopupJpaRepository extends JpaRepository<PopupJpaEntity, Long> {

    // 사용자별 팝업 목록 조회 (시작일 오름차순)
    @EntityGraph(attributePaths = {"reservation", "reservation.space"}) // N+1 방지
    List<PopupJpaEntity> findByReservationUserUserIdOrderByStartDateAsc(Long userId);
}