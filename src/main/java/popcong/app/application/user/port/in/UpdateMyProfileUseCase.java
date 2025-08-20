package popcong.app.application.user.port.in;


public interface UpdateMyProfileUseCase {
    void update(Long userId,
                String name,
                String introduction,
                String profileImageUrl
    );

}
