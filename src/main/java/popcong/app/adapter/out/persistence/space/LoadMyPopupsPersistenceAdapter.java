package popcong.app.adapter.out.persistence.space;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import popcong.app.adapter.out.persistence.space.entity.PopupJpaEntity;
import popcong.app.adapter.out.persistence.space.mapper.PopupEntityMapper;
import popcong.app.adapter.out.persistence.space.repository.PopupJpaRepository;
import popcong.app.application.space.dto.response.MyPopupItemResponseDto;
import popcong.app.application.user.port.out.LoadMyPopupsPort;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LoadMyPopupsPersistenceAdapter implements LoadMyPopupsPort {

    private final PopupJpaRepository popupJpaRepository;

    @Override
    public List<MyPopupItemResponseDto> findMyPopups(Long userId, int limit, int offset) {
        int safeLimit  = (limit <= 0) ? 5 : limit;
        int safeOffset = Math.max(offset, 0);
        int page = safeOffset / safeLimit;

        Pageable pageable = PageRequest.of(page, safeLimit);   // 정렬은 메서드명에 포함

        List<PopupJpaEntity> rows =
                popupJpaRepository.findByReservationUserUserIdOrderByStartDateDesc(userId, pageable);

        return rows.stream()
                .map(PopupEntityMapper::toMyPopupItemResponseDto)
                .toList();
    }
}