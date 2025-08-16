
package popcong.app.application.image.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.ImageS3ErrorCode;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    // 성공 시 S3 URL 반환
    public String uploadFile(String key, MultipartFile file) {

        // 입력 검증
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ImageS3ErrorCode.FILE_NOT_FOUND);
        }
        try {
            //메타 데이터 설정
            ObjectMetadata md = new ObjectMetadata();
            md.setContentLength(file.getSize()); // S3가 스트림을 읽을 크기
            if (file.getContentType() != null) { //브라우저/클라이언트가 파일을 다룰때 도움
                md.setContentType(file.getContentType()); //다운로드 표시에 유용한 헤더
            }

            // 로그확인
            log.info("[S3 PUT] bucket={}, key={}, size={}, contentType={}",
                    bucket, key, file.getSize(), file.getContentType());

            //key "버킷 내부 경로/파일명", 호출자가 key를 만들어 넘김 -> 서비스는 업로드만 담당
            amazonS3.putObject(bucket, key, file.getInputStream(), md);
            return amazonS3.getUrl(bucket, key).toString(); //getUrl -> 저장 위치 식별자로 씀

        } catch (IOException e) {
            log.warn("S3 upload IOException (key={}): {}", key, e.getMessage(), e);
            throw new BusinessException(ImageS3ErrorCode.FILE_UPLOAD_ERROR);

        } catch (AmazonServiceException e) {
            //S3가 HTTP 오류로 응답한 경우
            int status = e.getStatusCode();
            log.warn("S3 upload failed: status={}, awsCode={}, msg={}",
                    status, e.getErrorCode(), e.getMessage(), e);

            if (status == 403) throw new BusinessException(ImageS3ErrorCode.STORAGE_ACCESS_DENIED); //권한 없음
            if (status == 404) throw new BusinessException(ImageS3ErrorCode.FILE_NOT_FOUND); // 파일 없음
            if (status == 413) throw new BusinessException(ImageS3ErrorCode.FILE_TOO_LARGE); //용량

            throw new BusinessException(ImageS3ErrorCode.FILE_UPLOAD_ERROR);

        } catch (RuntimeException e) { //네트워크 문제등을 포함한 대부분 런타임 문제
        log.warn("S3 client/runtime error (key={}): {}", key, e.getMessage(), e);
        throw new BusinessException(ImageS3ErrorCode.FILE_UPLOAD_ERROR);

        }
    }
}