package popcong.app.global.exception.error;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import popcong.app.global.exception.ErrorCode;

    @Getter
    @RequiredArgsConstructor
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum ReservationErrorCode implements ErrorCode {

        // 400
        RESERVATION_INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST.value(), "R4001", "날짜 형식 오류 또는 종료일이 시작일 이전입니다."),
        INVALID_REQUEST(HttpStatus.BAD_REQUEST.value(), "R4002", "잘못된 요청입니다."),
        UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "R4010", "인증이 필요합니다."),
        FORBIDDEN_USER(HttpStatus.FORBIDDEN.value(), "R4030", "접근 권한이 없습니다."),
        RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "R4040", "요청한 예약을 찾을 수 없습니다."),
        SPACE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "R4041", "요청한 공간을 찾을 수 없습니다."),
        CHAT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "R4042", "요청한 채팅방을 찾을 수 없습니다."),
        RESERVATION_OVERLAPPED(HttpStatus.CONFLICT.value(), "R4091", "해당 기간에 이미 예약이 존재합니다."),
        RESERVATION_DUPLICATE(HttpStatus.CONFLICT.value(), "R4092", "같은 요청으로 중복 생성되었습니다."),
        RENTAL_FEE_MISMATCH(HttpStatus.UNPROCESSABLE_ENTITY.value(), "R4220", "요청 금액과 서버 계산 금액이 일치하지 않습니다."),

        // 500
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "R5000", "서버 내부 오류가 발생했습니다.");

        private final int httpStatus;
        private final String code;
        private final String message;
    }
