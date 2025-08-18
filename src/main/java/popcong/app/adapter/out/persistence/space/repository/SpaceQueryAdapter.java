package popcong.app.adapter.out.persistence.space.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import popcong.app.adapter.out.persistence.space.entity.QSpaceJpaEntity;
import popcong.app.adapter.out.persistence.space.entity.SpaceJpaEntity;
import popcong.app.adapter.out.persistence.space.mapper.SpaceMapper;
import popcong.app.application.space.dto.response.SpaceDetailDto;
import popcong.app.application.space.port.out.SpaceQueryPort;
import popcong.app.domain.space.model.Space;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.SpaceErrorCode;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SpaceQueryAdapter implements SpaceQueryPort {

    private final JPAQueryFactory queryFactory;
    private final SpaceMapper spaceMapper;
    private final SpaceJpaRepository spaceJpaRepository;

    public List<Space> findPopularSpaceTop20() {
        QSpaceJpaEntity spaceEntity = QSpaceJpaEntity.spaceJpaEntity;

        List<SpaceJpaEntity> entities = queryFactory
                .selectFrom(spaceEntity)
                .orderBy(spaceEntity.views.desc())
                .limit(20)
                .fetch();

        return entities.stream().map(spaceMapper::toDomain).toList();
    };

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
