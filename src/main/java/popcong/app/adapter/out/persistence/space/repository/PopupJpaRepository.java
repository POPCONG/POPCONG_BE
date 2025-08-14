package popcong.app.adapter.out.persistence.space.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import popcong.app.adapter.out.persistence.space.entity.PopupJpaEntity;

import java.util.List;

public interface PopupJpaRepository extends JpaRepository<PopupJpaEntity, Long> {


     // 사용자별 팝업 목록 조회 (최신 시작일 내림차순)
    @EntityGraph(attributePaths = {"reservation", "reservation.space"}) //fetch 지정하여 추가쿼리 막음
    List<PopupJpaEntity> findByReservationUserUserIdOrderByStartDateDesc(Long userId, Pageable pageable);
}