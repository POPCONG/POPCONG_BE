package popcong.app.adapter.out.persistence.image.mapper;

import org.springframework.stereotype.Component;
import popcong.app.adapter.out.persistence.image.entity.ImageJpaEntity;
import popcong.app.domain.image.model.Image;

@Component
public class ImageMapper {

    public Image toDomain(ImageJpaEntity imageJpaEntity) {
        return new Image(
                imageJpaEntity.getImageId(),
                imageJpaEntity.getImageUrl(),
                imageJpaEntity.getSaveOrder(),
                imageJpaEntity.getImageableId(),
                imageJpaEntity.getImageableType(),
                imageJpaEntity.getCreatedAt()
        );
    }
}
