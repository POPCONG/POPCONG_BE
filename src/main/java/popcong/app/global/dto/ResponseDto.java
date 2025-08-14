package popcong.app.global.dto;

import org.springframework.http.HttpStatusCode;

public record ResponseDto<T>(
        int statusCode,
        String message,
        T data
) {
    // data -> X
    public static <T> ResponseDto<T> success(
            final HttpStatusCode statusCode,
            final String message
    ) {
        return new ResponseDto<>(
                statusCode.value(),
                message,
                null
        );
    }

    // data -> O
    public static <T> ResponseDto<T> success(
            final HttpStatusCode statusCode,
            final String message,
            final T data
    ) {
        return new ResponseDto<>(
                statusCode.value(),
                message,
                data
        );
    }
}