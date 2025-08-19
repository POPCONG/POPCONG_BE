package popcong.app.application.user.port.out;

import java.time.LocalDateTime;

/**
 * 위시 생성/삭제 커맨드 포트
 */

public interface WishCommandPort {
    Long insert(Long userId, Long spaceId, LocalDateTime createdAt);
    void delete(Long userId, Long spaceId);
}