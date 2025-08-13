package popcong.app.application.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import popcong.app.domain.auth.model.OAuth2SignInCommand;
import popcong.app.domain.auth.model.SignUpCommand;
import popcong.app.domain.auth.port.in.OAuth2SignUpUseCase;
import popcong.app.domain.user.model.Role;
import popcong.app.domain.user.model.User;
import popcong.app.domain.user.model.UserRole;
import popcong.app.domain.user.port.out.LoadUserPort;
import popcong.app.domain.user.port.out.SaveUserPort;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuth2SignUpService implements OAuth2SignUpUseCase {

    private final SaveUserPort saveUserPort;
    private final LoadUserPort loadUserPort;

    @Override
    public User signUp(OAuth2SignInCommand oAuth2Info, SignUpCommand userInfo) {
        if (loadUserPort.loadUserByEmail(oAuth2Info.email()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        User newUser = new User(
                null,
                oAuth2Info.provider(),
                oAuth2Info.providerId(),
                oAuth2Info.email(),
                userInfo.name(),
                userInfo.profileImageUrl(),
                userInfo.introduction(),
                Role.ROLE_USER,
                (userInfo.isUploaded()) ? UserRole.PENDING : UserRole.GUEST,
                LocalDateTime.now(),
                null
        );
        return saveUserPort.saveUser(newUser);
    }
}