package popcong.app.application.user.port.in;

public interface LikeSpaceUseCase {
    Result like(Long userId, Long spaceId);

    record Result(Long userId, Long spaceId, String createdAtIso) {}
}