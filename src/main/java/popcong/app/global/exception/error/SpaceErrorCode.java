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
    INVALID_PRICE_ERROR(HttpStatus.BAD_REQUEST.value(), "S4001", "금액 범위가 잘못되었습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}
