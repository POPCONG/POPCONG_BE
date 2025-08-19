package popcong.app.adapter.out.persistence.image.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import popcong.app.adapter.out.persistence.image.mapper.ImageMapper;
import popcong.app.application.image.port.out.ImageQueryPort;
import popcong.app.domain.image.model.Image;
import popcong.app.domain.image.model.ImageableType;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageQueryAdapter implements ImageQueryPort {

    private final ImageJpaRepository imageJpaRepository;
    private final ImageMapper imageMapper;

    public List<Image> findSpaceImages(Long imageableId, ImageableType imageableType, int limit) {

        return imageJpaRepository
                .findByImageableIdAndImageableTypeOrderBySaveOrderAsc(imageableId, imageableType)
                .stream()
                .limit(limit)
                .map(imageMapper::toDomain)
                .toList();
    }
}
