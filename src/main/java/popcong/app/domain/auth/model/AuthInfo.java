package popcong.app.domain.auth.model;

import popcong.app.domain.user.model.Role;
import popcong.app.domain.user.model.User;

public record AuthInfo(
        Long id,
        String email,
        Role role
) {
    public static AuthInfo from(User user) {
        return new AuthInfo(
                user.userId(),
                user.email(),
                user.role()
        );
    }
}