package popcong.app.application.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import popcong.app.application.user.port.in.UserInfoUseCase;
import popcong.app.application.user.port.out.LoadUserPort;
import popcong.app.application.user.port.out.SaveUserPort;
import popcong.app.domain.user.model.User;
import popcong.app.domain.user.model.UserRole;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.AuthErrorCode;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInfoService implements UserInfoUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;

    // Guest 유저로 복귀
    public User updateUserRoleToGuest(Long userId) {
        User user = loadUserPort.loadUserById(userId)
                .orElseThrow(() -> new BusinessException(AuthErrorCode.USER_NOT_FOUND));
        User updated = user.withUserRole(UserRole.GUEST);
        return saveUserPort.saveUser(updated);
    };

    // 일반 유저로 변경
    public User updateUserRoleToGeneral(Long userId) {
        User user = loadUserPort.loadUserById(userId)
                .orElseThrow(() -> new BusinessException(AuthErrorCode.USER_NOT_FOUND));
        User updated = user.withUserRole(UserRole.GENERAL);
        return saveUserPort.saveUser(updated);
    };

    // 호스트 인증 대기로 변경
    public User updateUserRoleToPending(Long userId) {
        User user = loadUserPort.loadUserById(userId)
                .orElseThrow(() -> new BusinessException(AuthErrorCode.USER_NOT_FOUND));
        User updated = user.withUserRole(UserRole.PENDING);
        return saveUserPort.saveUser(updated);
    };

    // Host 유저로 변경
    public User updateUserRoleToHost(Long userId) {
        User user = loadUserPort.loadUserById(userId)
                .orElseThrow(() -> new BusinessException(AuthErrorCode.USER_NOT_FOUND));
        User updated = user.withUserRole(UserRole.HOST);
        return saveUserPort.saveUser(updated);
    };
}
