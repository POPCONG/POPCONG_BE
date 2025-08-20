package popcong.app.application.user.port.out;

import popcong.app.domain.user.model.UserRole;

public interface UpdateUserPort {

    void updateProfile(Long userId, String name, String introduction, String profileImageUrl, UserRole role);
}
