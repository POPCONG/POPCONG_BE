package popcong.app.application.user.port.out;

/**
 * Space 존재 여부 확인 포트
 */
public interface SpaceLookupPort {
    boolean existsById(Long spaceId);
}