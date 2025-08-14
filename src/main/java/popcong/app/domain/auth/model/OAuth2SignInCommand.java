package popcong.app.domain.auth.model;

import popcong.app.domain.user.model.Provider;

public record OAuth2SignInCommand(
        Provider provider,
        String providerId,
        String email
) {}