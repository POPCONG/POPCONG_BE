package popcong.app.adapter.out.persistence.space.mapper;

import popcong.app.adapter.out.persistence.space.entity.PopupJpaEntity;
import popcong.app.adapter.out.persistence.space.entity.ReservationJpaEntity;
import popcong.app.adapter.out.persistence.space.entity.SpaceJpaEntity;
import popcong.app.application.space.dto.response.MyPopupItemResponseDto;
import popcong.app.application.space.dto.response.SpaceInfoResponseDto;

public final class PopupEntityMapper {
    private PopupEntityMapper() {} // 유틸클래스(인스턴스 생성 방지)

    public static MyPopupItemResponseDto toMyPopupItemResponseDto(PopupJpaEntity popup) {
        //팝업이 속한 예약 엔티티 가져오기
        ReservationJpaEntity r = popup.getReservation();
        //예약 O -> 예약이 속한 공간 엔티티 가져오기
        SpaceJpaEntity s = (r != null) ? r.getSpace() : null;

        //MyPopupItemResponseDto 생성
        return new MyPopupItemResponseDto(
                SpaceInfoResponseDto.of( //공간정보 부분 생성
                        (s != null) ? s.getSpaceId() : null, //공간 ID
                        (s != null) ? s.getSpaceName() : null,     // 공간 이름
                        (s != null && s.getFloor() != null)
                                ? String.valueOf(s.getFloor())
                                : null,
                        //층 O -> String 으로 변환
                        (popup.getAddress() != null) //팝업 주소 O -> 사용, 팝업 주소 X -> 공간 주소 사용
                                ? popup.getAddress()
                                : (s != null ? s.getAddress() : null)
                ),
                popup.getPopupId(),
                popup.getName(),
                popup.getPopupStatus(),
                popup.getStartDate().toLocalDate(),
                popup.getEndDate().toLocalDate()
        );
    }
}