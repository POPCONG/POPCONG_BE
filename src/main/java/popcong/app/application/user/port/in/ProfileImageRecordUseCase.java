package popcong.app.application.user.port.in;

public interface ProfileImageRecordUseCase {
    void recordProfileImage(Long userId, String imageUrl);
}
