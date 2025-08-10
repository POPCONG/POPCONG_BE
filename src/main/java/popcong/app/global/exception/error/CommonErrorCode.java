package popcong.app.global.exception.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import popcong.app.global.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CommonErrorCode implements ErrorCode {

    // 4XX
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST.value(), "C4001", "Invalid Type Value"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST.value(), "C4002", "Invalid Input Value"),
    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "C4003", "Entity Not Found"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "C4004", "Invalid Http Method"),

    // 5XX
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "C5001", "Internal Server Error");


    private final int httpStatus;
    private final String code;
    private final String message;
}
