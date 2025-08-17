package popcong.app.application.image.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import popcong.app.adapter.out.persistence.image.entity.ImageJpaEntity;
import popcong.app.adapter.out.persistence.image.repository.ImageJpaRepository;
import popcong.app.domain.image.model.ImageableType;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.ImageS3ErrorCode;

import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Service s3Service;  // S3 업로드 담당
    private final ImageJpaRepository imageRepository; // Image 테이블 JPA

    /** 업로드 + DB 저장 */
    public ImageJpaEntity upload(
                                 ImageableType imageableType,
                                 Long imageableId,
                                 int saveOrder,
                                 MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new BusinessException(ImageS3ErrorCode.FILE_NOT_FOUND);
        }

        // 키 생성 (profile/123/0.jpg)
        String key = buildKey(imageableType, imageableId, saveOrder, file);

        // S3 업로드 후 URL 반환
        String url = s3Service.uploadFile(key, file);

        // DB에 저장 (있으면 업데이트, 없으면 새로 생성)
        ImageJpaEntity entity = imageRepository
                .findByImageableTypeAndImageableIdAndSaveOrder(imageableType, imageableId, Integer.valueOf(saveOrder))
                .map(existing -> ImageJpaEntity.builder()
                        .imageId(existing.getImageId())
                        .imageUrl(url)
                        .saveOrder(saveOrder)
                        .imageableId(imageableId)
                        .imageableType(imageableType)
                        .build()
                )
                .orElseGet(() -> ImageJpaEntity.builder()
                        .imageUrl(url)
                        .saveOrder(saveOrder)
                        .imageableId(imageableId)
                        .imageableType(imageableType)
                        .build()
                );

        return imageRepository.save(entity);
    }

    /** 목록 조회 */
    public List<ImageJpaEntity> list(Long imageableId, ImageableType imageableType) {
        return imageRepository.findByImageableIdAndImageableTypeOrderBySaveOrderAsc(
                imageableId, imageableType
        );
    }

    // 내부 유틸

    private String buildKey(ImageableType type, Long imageableId, int saveOrder, MultipartFile file) {
        String prefix = type.name().toLowerCase(Locale.ROOT);
        String ext = guessExt(file);
        return prefix + "/" + imageableId + "/" + saveOrder + "." + ext;
    }

    private String guessExt(MultipartFile file) {
        String name = file.getOriginalFilename();
        if (name != null) {
            int dot = name.lastIndexOf('.');
            if (dot > -1 && dot < name.length() - 1) {
                return name.substring(dot + 1).toLowerCase(Locale.ROOT);
            }
        }
        return "jpg";
    }
}