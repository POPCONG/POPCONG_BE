package popcong.app.domain.auth.port.in;

import popcong.app.domain.auth.model.OAuth2SignInCommand;
import popcong.app.domain.user.model.User;

// 로그인(사용자 인증 후 토큰 발급) 인터페이스
public interface OAuth2SignInUseCase {
    User signIn(OAuth2SignInCommand command);
}