package popcong.app.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    int getHttpStatus();
    String getCode();
    String getMessage();

    default HttpStatus getHttpStatusEnum() {
        return HttpStatus.valueOf(getHttpStatus());
    }

    default void validate() {
        if (getHttpStatus() < 100 || getHttpStatus() > 599) {
            throw new IllegalArgumentException("Invalid HTTP status: " + getHttpStatus());
        }
        if (getCode() == null || getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Error code cannot be null or empty");
        }
    }
}
