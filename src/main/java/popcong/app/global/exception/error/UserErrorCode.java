package popcong.app.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import popcong.app.global.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    // wishlist 4xx

    ALREADY_LIKED(400, "C4003", "이미 좋아요를 누른 공간입니다."),
    NOT_LIKED(400, "C4004", "좋아요 상태가 아닌 공간입니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}