package popcong.app.application.user.port.in;

import popcong.app.domain.user.model.User;

public interface UserInfoUseCase {
    User updateUserRoleToGeneral(Long userId);
    User updateUserRoleToGuest(Long userId);
    User updateUserRoleToPending(Long userId);
    User updateUserRoleToHost(Long userId);
}
