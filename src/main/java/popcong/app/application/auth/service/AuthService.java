package popcong.app.application.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import popcong.app.adapter.out.jwt.JwtUtils;
import popcong.app.application.auth.port.in.OAuth2SignInUseCase;
import popcong.app.application.user.port.out.LoadUserPort;
import popcong.app.application.user.port.out.SaveUserPort;
import popcong.app.domain.auth.model.OAuth2SignInCommand;
import popcong.app.domain.user.model.Role;
import popcong.app.domain.user.model.User;
import popcong.app.domain.user.model.UserRole;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements OAuth2SignInUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;

    private final JwtUtils jwtUtils;

    @Override
    public User findOrCreateUser(OAuth2SignInCommand command) {
        return loadUserPort.loadUserByEmail(command.email())
                .orElseGet(() -> {
                    User newUser = new User(
                            null,
                            command.provider(),
                            command.providerId(),
                            command.email(),
                            "GUEST",
                            null,
                            null,
                            Role.ROLE_USER,
                            UserRole.GUEST,
                            LocalDateTime.now(),
                            null
                    );
                    return saveUserPort.saveUser(newUser);
                });
    }
}
