package popcong.app.domain.auth.port.in;

import popcong.app.domain.auth.model.OAuth2SignInCommand;
import popcong.app.domain.auth.model.SignUpCommand;
import popcong.app.domain.user.model.User;

// 로그인(사용자 인증 후 토큰 발급) 인터페이스
public interface OAuth2SignInUseCase {
    // 카카오 정보만으로 로그인 및 기본 사용자 생성
    User signIn(OAuth2SignInCommand oAuth2SignInCommand);
}