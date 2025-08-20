package popcong.app.application.user.port.in;

import popcong.app.domain.user.model.UserRole;

public interface UpdateMyProfileUseCase {
    void update(Long userId,
                String name,
                String introduction,
                String profileImageUrl,
                UserRole role
    );

}
