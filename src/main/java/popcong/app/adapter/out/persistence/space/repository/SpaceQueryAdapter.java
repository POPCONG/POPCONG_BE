package popcong.app.adapter.out.persistence.space.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import popcong.app.adapter.out.persistence.space.entity.QSpaceJpaEntity;
import popcong.app.adapter.out.persistence.space.entity.SpaceJpaEntity;
import popcong.app.adapter.out.persistence.space.mapper.SpaceMapper;
import popcong.app.application.space.port.out.SpaceQueryPort;
import popcong.app.domain.space.model.Space;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SpaceQueryAdapter implements SpaceQueryPort {

    private final JPAQueryFactory queryFactory;
    private final SpaceMapper spaceMapper;

    public List<Space> findPopularSpaceTop20() {
        QSpaceJpaEntity spaceEntity = QSpaceJpaEntity.spaceJpaEntity;

        List<SpaceJpaEntity> entities = queryFactory
                .selectFrom(spaceEntity)
                .orderBy(spaceEntity.views.desc())
                .limit(20)
                .fetch();

        return entities.stream().map(spaceMapper::toDomain).toList();
    };
}
