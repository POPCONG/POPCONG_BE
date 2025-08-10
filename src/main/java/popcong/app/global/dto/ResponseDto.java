package popcong.app.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

    private final String statusCode;
    private final String message;
    private final T data;

    // data -> X
    public static <T> ResponseDto<T> res(
            final HttpStatusCode statusCode,
            final String message
    ) {
        return new ResponseDto<>(
                String.valueOf(statusCode.value()),
                message,
                null
        );
    }

    // data -> O
    public static <T> ResponseDto<T> res(
            final HttpStatusCode statusCode,
            final String message,
            final T data
    ) {
        return new ResponseDto<>(
                String.valueOf(statusCode.value()),
                message,
                data
        );
    }
}