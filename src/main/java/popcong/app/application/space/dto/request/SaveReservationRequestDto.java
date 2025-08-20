package popcong.app.application.space.dto.request;

import java.time.LocalDate;

public record SaveReservationRequestDto(
        LocalDate startDate,    //시작일
        LocalDate endDate, //종료일
        Integer rentalFee, //하루 대여료
        Integer totalRentalFee, // 총 대여료
        Integer deposit // 보증금
) {}
