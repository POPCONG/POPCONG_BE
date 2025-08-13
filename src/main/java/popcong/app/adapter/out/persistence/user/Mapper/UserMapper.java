package popcong.app.adapter.out.persistence.user.Mapper;

import org.springframework.stereotype.Component;
import popcong.app.adapter.out.persistence.user.entity.UserJpaEntity;
import popcong.app.domain.user.model.User;

@Component
public class UserMapper {

    // Entity -> Domain
    public User toDomain(UserJpaEntity entity) {
        return new User(
                entity.getId(),
                entity.getProvider(),
                entity.getProviderId(),
                entity.getEmail(),
                entity.getName(),
                entity.getProfileImageUrl(),
                entity.getIntroduction(),
                entity.getRole(),
                entity.getUserRole(),
                entity.getCreatedAt(),
                entity.getDeletedAt()
        );
    }

    // Domain -> Entity
    public UserJpaEntity toEntity(User domain) {
        return UserJpaEntity.builder()
                .id(domain.id())
                .provider(domain.provider())
                .providerId(domain.providerId())
                .email(domain.email())
                .name(domain.name())
                .profileImageUrl(domain.profileImageUrl())
                .introduction(domain.introduction())
                .role(domain.role())
                .userRole(domain.userRole())
                .createdAt(domain.createdAt())
                .deletedAt(domain.deletedAt())
                .build();
    }
}
