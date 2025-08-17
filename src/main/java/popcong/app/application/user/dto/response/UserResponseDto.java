package popcong.app.application.user.dto.response;

import popcong.app.domain.user.model.Provider;
import popcong.app.domain.user.model.Role;
import popcong.app.domain.user.model.User;
import popcong.app.domain.user.model.UserRole;

import java.time.LocalDateTime;

public record UserResponseDto(
        Long userId,
        Provider provider,
        String providerId,
        String email,
        String name,
        String profileImageUrl,
        String introduction,
        Role role,
        UserRole userRole,
        LocalDateTime createdAt,
        LocalDateTime deletedAt
) {
    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.userId(),
                user.provider(),
                user.providerId(),
                user.email(),
                user.name(),
                user.profileImageUrl(),
                user.introduction(),
                user.role(),
                user.userRole(),
                user.createdAt(),
                user.deletedAt()
        );
    }
}
