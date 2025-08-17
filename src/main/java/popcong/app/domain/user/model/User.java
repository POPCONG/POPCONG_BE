package popcong.app.domain.user.model;

import java.time.LocalDateTime;

public record User (
    Long userId,
    Provider provider, // 소셜로그인 제공자
    String providerId, // 소셜로그인에서 제공받은 ID
    String email, // 소셜로그인 email
    String name,
    String profileImageUrl,
    String introduction,
    Role role, // user-admin
    UserRole userRole, // guest-host
    LocalDateTime createdAt,
    LocalDateTime deletedAt
) {
    public User withUserRole(UserRole userRole) {
        return new User(
                this.userId,
                this.provider,
                this.providerId,
                this.email,
                this.name,
                this.profileImageUrl,
                this.introduction,
                this.role,
                userRole,
                this.createdAt,
                this.deletedAt
        );
    }
}
