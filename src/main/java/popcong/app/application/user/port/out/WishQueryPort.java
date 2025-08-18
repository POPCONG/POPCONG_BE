package popcong.app.application.user.port.out;

/**
 * userId, spaceId 기준으로 현재 좋아요 상태 확인
 */
public interface WishQueryPort {
    boolean exists(Long userId, Long spaceId);
}