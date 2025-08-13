package popcong.app.domain.auth.port.in;

import popcong.app.domain.auth.model.OAuth2SignInCommand;
import popcong.app.domain.auth.model.SignUpCommand;
import popcong.app.domain.user.model.User;

public interface OAuth2SignUpUseCase {
    User signUp(OAuth2SignInCommand oAuth2SignInCommand, SignUpCommand signUpCommand);
}
