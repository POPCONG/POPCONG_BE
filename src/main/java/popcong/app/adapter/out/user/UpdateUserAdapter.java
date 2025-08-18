// popcong.app.adapter.out.user.UpdateUserAdapter
package popcong.app.adapter.out.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import popcong.app.adapter.out.persistence.user.entity.UserJpaEntity;
import popcong.app.adapter.out.persistence.user.repository.UserJpaRepository;
import popcong.app.application.user.port.out.UpdateUserPort;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.AuthErrorCode;

@Component
@RequiredArgsConstructor
public class UpdateUserAdapter implements UpdateUserPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    @Transactional
    public void updateProfile(Long userId, String name, String introduction, String profileImageUrl) {
        UserJpaEntity user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(AuthErrorCode.USER_NOT_FOUND));

        if (name != null) user.changeName(name);
        if (introduction != null) user.changeIntroduction(introduction);
        if (profileImageUrl != null) user.changeProfileImageUrl(profileImageUrl);


    }
}