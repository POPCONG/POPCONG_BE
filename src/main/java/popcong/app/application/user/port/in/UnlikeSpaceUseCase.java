package popcong.app.application.user.port.in;

public interface UnlikeSpaceUseCase {
    record Result(Long userId, Long spaceId) {}
    Result unlike(Long userId, Long spaceId);
}