package popcong.app.application.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import popcong.app.domain.auth.model.OAuth2SignInCommand;
import popcong.app.domain.auth.model.SignUpCommand;
import popcong.app.domain.auth.port.in.OAuth2SignInUseCase;
import popcong.app.domain.user.model.Role;
import popcong.app.domain.user.model.User;
import popcong.app.domain.user.model.UserRole;
import popcong.app.domain.user.port.out.LoadUserPort;
import popcong.app.domain.user.port.out.SaveUserPort;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuth2SignInService implements OAuth2SignInUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;

    @Override
    public User signIn(OAuth2SignInCommand oAuth2Info, SignUpCommand userInfo) {
        return loadUserPort.loadUserByEmail(oAuth2Info.email())
                .orElseGet(() -> {
                    User newUser = new User(
                            null,
                            oAuth2Info.provider(),
                            oAuth2Info.providerId(),
                            oAuth2Info.email(),
                            userInfo.name(),
                            userInfo.profileImageUrl(),
                            userInfo.introduction(),
                            Role.ROLE_USER,
                            UserRole.GUEST,
                            LocalDateTime.now(),
                            null
                    );
                    return saveUserPort.saveUser(newUser);
                });
    }
}
