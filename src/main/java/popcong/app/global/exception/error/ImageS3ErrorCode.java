package popcong.app.global.exception.error;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import popcong.app.global.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ImageS3ErrorCode implements ErrorCode {

    //4xx
    FILE_NOT_FOUND        (HttpStatus.NOT_FOUND.value(),           "IMG4040", "요청한 파일을 찾을 수 없습니다."),
    FILE_TOO_LARGE        (HttpStatus.PAYLOAD_TOO_LARGE.value(),   "IMG4130", "파일 크기 제한을 초과했습니다."),
    STORAGE_ACCESS_DENIED (HttpStatus.FORBIDDEN.value(),           "IMG4030", "스토리지 접근 권한이 없습니다."),
    INVALID_CONTENT_TYPE  (HttpStatus.BAD_REQUEST.value(),         "IMG4001", "지원하지 않는 콘텐츠 타입입니다."),

    // 5xx
    FILE_UPLOAD_ERROR     (HttpStatus.INTERNAL_SERVER_ERROR.value(),"IMG5001", "파일 업로드 중 오류가 발생했습니다."),
    FILE_DOWNLOAD_ERROR   (HttpStatus.INTERNAL_SERVER_ERROR.value(),"IMG5002", "파일 다운로드 중 오류가 발생했습니다."),
    FILE_DELETE_ERROR     (HttpStatus.INTERNAL_SERVER_ERROR.value(),"IMG5003", "파일 삭제 중 오류가 발생했습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}
