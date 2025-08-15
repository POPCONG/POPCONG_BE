package popcong.app.global.exception.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import popcong.app.global.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SpaceErrorCode implements ErrorCode {

    // 400
    INVALID_PRICE_ERROR(HttpStatus.BAD_REQUEST.value(), "S4001", "금액 범위가 잘못되었습니다."),
    UNSUPPORTED_SORT_TYPE(HttpStatus.BAD_REQUEST.value(), "S4002", "지원하지 않는 정렬 타입입니다."),
    INVALID_BOUDING_BOX(HttpStatus.BAD_REQUEST.value(), "S4003", "탐색 범위가 잘못됐습니다."),;

    private final int httpStatus;
    private final String code;
    private final String message;
}
