package popcong.app.domain.user.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class User {
    private final Long id;
    private final Provider provider; // 소셜로그인 제공자
    private final String providerId; // 소셜로그인에서 제공받은 ID
    private final String email; // 소셜로그인 email
    private final String name;
    private final String profileImageUrl;
    private final String introduction;
    private final Role role; // user-admin
    private final UserRole userRole; // guest-host
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;
}
