package popcong.app.application.auth.port.in;

import popcong.app.domain.auth.model.AuthInfo;

public interface TokenGenerateUseCase {
    String generateToken(AuthInfo authInfo);
}