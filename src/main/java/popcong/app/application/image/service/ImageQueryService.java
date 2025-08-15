package popcong.app.application.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import popcong.app.adapter.out.persistence.image.mapper.CoverImageMapper;
import popcong.app.adapter.out.persistence.image.repository.ImageJpaRepository;
import popcong.app.application.image.port.in.ImageQueryUseCase;
import popcong.app.domain.image.model.ImageableType;

@Service
@RequiredArgsConstructor
public class ImageQueryService implements ImageQueryUseCase {

    private final ImageJpaRepository imageJpaRepository;
    private final CoverImageMapper coverImageMapper;

    public String getCoverImageUrl(ImageableType imageableType, Long imageableId) {
        return imageJpaRepository
                .findByImageableTypeAndImageableIdAndSaveOrder(imageableType, imageableId, 1)
                .map(coverImageMapper::toCoverImageUrlDto)
                .orElse(null);
    }
}
