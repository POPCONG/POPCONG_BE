package popcong.app.global.exception.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import popcong.app.global.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ChatErrorCode implements ErrorCode {

    // 400
    INVALID_CHAT_ROOM(HttpStatus.BAD_REQUEST.value(), "A4001", "채팅에 필요한 아이디가 누락되었습니다."),
    IS_NOT_PARTICIPANT(HttpStatus.BAD_REQUEST.value(), "A4002", "채팅 참가자가 아닙니다."),

    SELF_CHAT_NOT_ALLOWED(HttpStatus.CONFLICT.value(), "A4092", "본인과 채팅 상대가 같을 수 없습니다."),

    CHAT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "A4041", "채팅을 찾을 수 없습니다.");


    private final int httpStatus;
    private final String code;
    private final String message;
}
