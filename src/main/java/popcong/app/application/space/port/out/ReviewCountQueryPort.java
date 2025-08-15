package popcong.app.application.space.port.out;

import java.util.Collection;
import java.util.Map;

public interface ReviewCountQueryPort {
    // 리뷰 수 구하기
    Long countBySpaceId(long spaceId);

    // 여러 공간의 리뷰 수 한 번에 구하기
    Map<Long, Long> countBySpaceIds(Collection<Long> spaceIds);
}