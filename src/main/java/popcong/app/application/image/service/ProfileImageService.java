package popcong.app.application.image.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import popcong.app.adapter.out.persistence.image.entity.ImageJpaEntity;
import popcong.app.adapter.out.persistence.image.repository.ImageJpaRepository;
import popcong.app.domain.image.model.ImageableType;

@Service
@RequiredArgsConstructor
public class ProfileImageService {

    private final ImageJpaRepository imageRepo;

    /** 기존 PROFILE 이미지를 지우고 새 URL 1장으로 교체 (IMAGE 테이블만) */
    @Transactional
    public void replaceProfileImage(Long userId, String newUrl) {
        imageRepo.deleteByImageableTypeAndImageableId(ImageableType.PROFILE, userId);

        ImageJpaEntity entity = ImageJpaEntity.builder()
                .imageUrl(newUrl)
                .saveOrder(0)
                .imageableId(userId)
                .imageableType(ImageableType.PROFILE)
                .build();

        imageRepo.save(entity);
    }
}