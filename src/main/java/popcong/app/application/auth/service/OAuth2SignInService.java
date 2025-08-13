package popcong.app.application.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import popcong.app.domain.auth.model.OAuth2SignInCommand;
import popcong.app.domain.auth.port.in.OAuth2SignInUseCase;
import popcong.app.domain.user.model.Role;
import popcong.app.domain.user.model.User;
import popcong.app.domain.user.model.UserRole;
import popcong.app.domain.user.port.out.LoadUserPort;


@Service
@Transactional
@RequiredArgsConstructor
public class OAuth2SignInService implements OAuth2SignInUseCase {

    private final LoadUserPort loadUserPort;

    @Override
    public User signIn(OAuth2SignInCommand oAuth2Info) {
        return loadUserPort.loadUserByEmail(oAuth2Info.email())
                .orElseGet(() -> {
                    return new User(
                            null,
                            oAuth2Info.provider(),
                            oAuth2Info.providerId(),
                            oAuth2Info.email(),
                            "GUEST",
                            null,
                            null,
                            Role.ROLE_USER,
                            UserRole.GUEST,
                            null,
                            null
                    );
                });
    }
}
