package popcong.app.adapter.out.persistence.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import popcong.app.domain.user.model.Provider;
import popcong.app.domain.user.model.Role;
import popcong.app.domain.user.model.UserRole;

import java.time.LocalDateTime;

@Entity
@Table(name = "USER")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private Provider provider;

    @Column(name = "providerId", nullable = false, unique = true, length = 50)
    private String providerId;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "name", nullable = false, length = 10)
    private String name;

    @Column(name = "profileImageUrl")
    private String profileImageUrl;

    @Column(name = "introduction", length = 1000)
    private String introduction;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role = Role.ROLE_USER;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "userRole", nullable = false)
    private UserRole userRole = UserRole.GUEST;

    @Builder.Default
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt =  LocalDateTime.now();

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    //변경 메서드
    public void changeName(String name) {
        this.name = name;
    }

    public void changeIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void changeProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}