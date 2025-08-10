package popcong.app.global.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import popcong.app.global.exception.ErrorCode;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        if (errorCode == null) {
            throw new IllegalArgumentException("errorCode cannot be null");
        }
        errorCode.validate();
        this.errorCode = errorCode;
    }

    private BusinessException(ErrorCode errorCode) {
        super(errorCode != null ? errorCode.getMessage() : "Business Exception");

        if (errorCode == null) {
            throw new IllegalArgumentException("errorCode cannot be null");
        }

        errorCode.validate();
        this.errorCode = errorCode;
    }

    public int getStatusCode() {
        return errorCode.getHttpStatus();
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatusEnum();
    }
}
