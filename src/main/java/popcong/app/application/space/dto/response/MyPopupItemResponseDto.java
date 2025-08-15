package popcong.app.application.space.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import popcong.app.domain.space.model.PopupStatus;


import java.time.LocalDate;

//외부 응답 전용 Dto
@JsonInclude(JsonInclude.Include.NON_NULL) // 값이 Null인 필드를 응답에서 제외
public record MyPopupItemResponseDto(
        SpaceInfoResponseDto spaceInfo,
        Long popupId,
        String name,          // 팝업명
        PopupStatus popupStatus,
        LocalDate startDate,
        LocalDate endDate

){
    public static MyPopupItemResponseDto of(
            Long spaceId, String spaceName, String floor, String address,
            Long popupId, String name, PopupStatus status,
            LocalDate startDate, LocalDate endDate
    ) {
        return new MyPopupItemResponseDto(
                SpaceInfoResponseDto.of(spaceId, spaceName, floor, address),
                popupId, name, status, startDate, endDate
        );
    }
}