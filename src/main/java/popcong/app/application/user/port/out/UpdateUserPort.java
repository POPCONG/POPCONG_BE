package popcong.app.application.user.port.out;

public interface UpdateUserPort {

    void updateProfile(Long userId, String name, String introduction, String profileImageUrl);
}
