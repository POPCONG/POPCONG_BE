// src/main/java/popcong/app/application/space/service/GetSpaceDetailService.java
package popcong.app.application.space.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import popcong.app.adapter.out.persistence.image.entity.ImageJpaEntity;
import popcong.app.adapter.out.persistence.image.repository.ImageJpaRepository;
import popcong.app.adapter.out.persistence.space.entity.SpaceJpaEntity;
import popcong.app.adapter.out.persistence.space.repository.SpaceJpaRepository;
import popcong.app.application.space.dto.response.SpaceDetailResponseDto;
import popcong.app.application.user.port.out.WishQueryPort;
import popcong.app.domain.image.model.ImageableType;

import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.SpaceErrorCode;

@Service
@RequiredArgsConstructor
public class GetSpaceDetailService {

    private final SpaceJpaRepository spaceJpaRepository;
    private final WishQueryPort wishQueryPort;
    private final ImageJpaRepository imageJpaRepository;

    public SpaceDetailResponseDto execute(Long userId, Long spaceId) {
        SpaceJpaEntity space = spaceJpaRepository.findById(spaceId)
                .orElseThrow(() -> new BusinessException(SpaceErrorCode.SPACE_NOT_FOUND));

        boolean wished = wishQueryPort.exists(userId, spaceId);

        String cover = imageJpaRepository
                .findByImageableTypeAndImageableIdAndSaveOrder(ImageableType.SPACE, spaceId, 0)
                .map(ImageJpaEntity::getImageUrl)
                .orElseGet(() ->
                        imageJpaRepository
                                .findByImageableIdAndImageableTypeOrderBySaveOrderAsc(spaceId, ImageableType.SPACE)
                                .stream()
                                .findFirst()
                                .map(ImageJpaEntity::getImageUrl)
                                .orElse(null)
                );

        return new SpaceDetailResponseDto(
                space.getSpaceId(),
                userId,
                space.getSpaceName(),
                space.getSpaceType(),
                space.getSpaceApplicationType(),
                space.getSpaceRentalType(),
                space.getImageCount(),
                space.getDescription(),
                space.getDeposit(),
                space.getRentalFee(),
                space.getFloor(),
                space.getArea(),
                space.getRating(),
                space.getAddress(),
                space.getLocation(),
                space.getLatitude(),
                space.getLongitude(),
                space.getGeoAdvantage(),
                space.getMaxPeriod(),
                space.getStatus(),
                space.getViews(),
                wished,
                cover
        );
    }
}