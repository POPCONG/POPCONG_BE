package popcong.app.adapter.out.persistence.space.repository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import popcong.app.adapter.out.persistence.space.entity.PopupJpaEntity;
import popcong.app.adapter.out.persistence.space.mapper.PopupEntityMapper;
import popcong.app.application.space.dto.response.MyPopupItemResponseDto;
import popcong.app.application.user.port.out.LoadMyPopupsPort;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LoadMyPopupsPersistenceAdapter implements LoadMyPopupsPort {

    private final PopupJpaRepository popupJpaRepository;

    @Override
    public List<MyPopupItemResponseDto> findMyPopups(Long userId) {

        List<PopupJpaEntity> rows =
                popupJpaRepository.findByReservationUserUserIdOrderByStartDateAsc(userId);

        return rows.stream()
                .map(PopupEntityMapper::toMyPopupItemResponseDto)
                .toList();
    }
}