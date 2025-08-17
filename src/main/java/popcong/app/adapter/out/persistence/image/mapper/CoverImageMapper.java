package popcong.app.adapter.out.persistence.image.mapper;

import org.springframework.stereotype.Component;
import popcong.app.adapter.out.persistence.image.entity.ImageJpaEntity;

@Component
public class CoverImageMapper {

    public String toCoverImageUrlDto(ImageJpaEntity imageJpaEntity) {
        return imageJpaEntity.getImageUrl();
    }
}