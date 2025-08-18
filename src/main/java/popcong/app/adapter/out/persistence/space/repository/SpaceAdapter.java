// src/main/java/popcong/app/adapter/out/persistence/space/repository/SpaceAdapter.java
package popcong.app.adapter.out.persistence.space.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import popcong.app.adapter.out.persistence.space.entity.SpaceJpaEntity;
import popcong.app.application.space.dto.response.SpaceDetailDto;
import popcong.app.application.space.port.out.SpaceQueryPort;

import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.SpaceErrorCode;

@Component
@RequiredArgsConstructor
public class SpaceAdapter implements SpaceQueryPort {

    private final SpaceJpaRepository spaceJpaRepository;

    @Override
    public SpaceDetailDto findDetail(Long spaceId) {
        SpaceJpaEntity space = spaceJpaRepository.findById(spaceId)
                .orElseThrow(() -> new BusinessException(
                        SpaceErrorCode.SPACE_NOT_FOUND
                ));


        return new SpaceDetailDto(
                space.getSpaceId(),
                space.getSpaceName(),
                space.getAddress(),
                space.getRentalFee(),
                space.getDeposit(),
                space.getRating(),
                null,   // coverImageUrl
                false   // isWishlisted
        );
    }
}