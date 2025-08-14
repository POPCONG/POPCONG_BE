package popcong.app.auth;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import popcong.app.adapter.out.jwt.JwtUtils;
import popcong.app.application.user.port.out.SaveUserPort;
import popcong.app.domain.auth.model.AuthInfo;
import popcong.app.domain.user.model.Provider;
import popcong.app.domain.user.model.Role;
import popcong.app.domain.user.model.User;
import popcong.app.domain.user.model.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
@Transactional
@ActiveProfiles("local")
public class AuthTest {

    private static final Logger log = LoggerFactory.getLogger(AuthTest.class);

    @Autowired
    private SaveUserPort saveUserPort;

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void generateUserAndTokens() {
        String postfix = UUID.randomUUID().toString().substring(0, 8);
        User user = new User(
                null,
                Provider.kakao,
                postfix,
                postfix + "@gmail.com",
                "김팝콩",
                null,
                null,
                Role.ROLE_USER,
                UserRole.GUEST,
                LocalDateTime.now(),
                null
        );

        User saved = saveUserPort.saveUser(user);
        log.info("테스트 사용자 생성: id={}, email={}", saved.userId(), saved.email());

        AuthInfo authInfo = AuthInfo.from(saved);
        String accessToken = jwtUtils.generateAccessToken(authInfo);
        String refreshToken = jwtUtils.generateRefreshToken(authInfo);

        log.info("🔑 AccessToken : {}", accessToken);
        log.info("🔑 RefreshToken : {}", refreshToken);

        boolean valid = jwtUtils.validateToken(accessToken);
        log.info("valid test : {}", valid);
        assert valid;
    }
}
