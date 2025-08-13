package popcong.app.domain.auth.port.in;

import popcong.app.domain.auth.model.AuthInfo;

public interface TokenGenerateUseCase {
    String generateToken(AuthInfo authInfo);
}