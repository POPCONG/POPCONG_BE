package popcong.app.adapter.out.persistence.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import popcong.app.application.user.dto.response.MyWishItemDto;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static popcong.app.adapter.out.persistence.image.entity.QImageJpaEntity.imageJpaEntity;
import static popcong.app.adapter.out.persistence.space.entity.QSpaceJpaEntity.spaceJpaEntity;
import static popcong.app.adapter.out.persistence.user.entity.QWishlistJpaEntity.wishlistJpaEntity;
import static popcong.app.domain.image.model.ImageableType.SPACE;

@RequiredArgsConstructor
public class WishlistQueryRepositoryImpl implements WishlistQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MyWishItemDto> findMyWishlist(Long userId) {
        var w = wishlistJpaEntity;
        var s = spaceJpaEntity;
        var i = imageJpaEntity;

        var coverImageSub = select(i.imageUrl)
                .from(i)
                .where(i.imageableType.eq(SPACE)
                        .and(i.imageableId.eq(s.spaceId))
                        .and(i.saveOrder.eq(0)))
                .limit(1);

        return queryFactory
                .select(com.querydsl.core.types.Projections.constructor(
                        MyWishItemDto.class,
                        s.spaceId,
                        coverImageSub,
                        s.rentalFee,
                        s.deposit,
                        s.address,
                        s.rating.coalesce(0.0),
                        com.querydsl.core.types.dsl.Expressions.constant(true) // boolean
                ))
                .from(w)
                .join(w.space, s)
                .where(w.user.userId.eq(userId))
                .orderBy(w.createdAt.desc())
                .fetch();
    }
}

