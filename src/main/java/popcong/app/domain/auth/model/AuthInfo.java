package popcong.app.domain.auth.model;

import popcong.app.domain.user.model.Role;
import popcong.app.domain.user.model.User;
import popcong.app.domain.user.model.UserRole;

public record AuthInfo(
        Long id,
        String providerId,
        String email,
        Role role,
        UserRole userRole
) {
    public static AuthInfo from(User user) {
        return new AuthInfo(
                user.id(),
                user.providerId(),
                user.email(),
                user.role(),
                user.userRole()
        );
    }
}