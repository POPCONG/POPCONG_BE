package popcong.app.adapter.out.image;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import popcong.app.application.image.port.out.ProfileImageUploadPort;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.ImageS3ErrorCode;

import java.io.IOException;


@Slf4j
@Component
public class S3ProfileImageUploadAdapter implements ProfileImageUploadPort {

    private final AmazonS3 s3;
    private final String bucket = "popcongbucket";
    private final Regions region = Regions.US_EAST_2; //오하이오

    public S3ProfileImageUploadAdapter() {
        this.s3 = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .build();
    }

    @Override
    public String uploadProfileImage(Long userId, MultipartFile file) {
        String key = buildKey(userId, file.getOriginalFilename());

        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(file.getSize());
            meta.setContentType(file.getContentType());

            PutObjectRequest req = new PutObjectRequest(bucket, key, file.getInputStream(), meta);

            // ✅ 예외 상세 로그 추가
            try {
                s3.putObject(req);
                return "https://%s.s3.%s.amazonaws.com/%s"
                        .formatted(bucket, region.getName(), key);
            } catch (AmazonServiceException e) {
                // S3가 에러 응답을 보낸 경우 (권한, 리전 불일치 등)
                log.error("[S3 SERVICE] status={}, code={}, msg={}, bucket={}, key={}",
                        e.getStatusCode(), e.getErrorCode(), e.getErrorMessage(), bucket, key, e);
                throw new BusinessException(ImageS3ErrorCode.FILE_UPLOAD_ERROR);
            } catch (SdkClientException e) {
                // 네트워크, 자격증명 문제
                log.error("[S3 CLIENT] msg={}, bucket={}, key={}", e.getMessage(), bucket, key, e);
                throw new BusinessException(ImageS3ErrorCode.FILE_UPLOAD_ERROR);
            }

        } catch (IOException e) {
            log.error("[S3 IO] msg={}, bucket={}, key={}", e.getMessage(), bucket, key, e);
            throw new BusinessException(ImageS3ErrorCode.FILE_UPLOAD_ERROR);
        }
    }

    private String buildKey(Long userId, String original) {
        String safe = (original == null ? "unknown" : original).replaceAll("\\s+", "_");
        return "profiles/%d/%d_%s".formatted(userId, System.currentTimeMillis(), safe);
    }
}