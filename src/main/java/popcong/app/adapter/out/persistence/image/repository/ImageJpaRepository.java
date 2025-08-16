package popcong.app.adapter.out.persistence.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import popcong.app.adapter.out.persistence.image.entity.ImageJpaEntity;
import popcong.app.domain.image.model.ImageableType;

import java.util.List;
import java.util.Optional;

public interface ImageJpaRepository extends JpaRepository<ImageJpaEntity, Long> {

    Optional<ImageJpaEntity> findByImageableTypeAndImageableIdAndSaveOrder(
            ImageableType imageableType, Long imageableId, Integer saveOrder
    );

    List<ImageJpaEntity> findByImageableIdAndImageableTypeOrderBySaveOrderAsc(
            Long imageableId, ImageableType imageableType
    );
}