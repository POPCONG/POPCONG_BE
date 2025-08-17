package popcong.app.application.user.port.in;

import popcong.app.domain.user.model.User;

public interface GetMyProfileUseCase {
    User getMyProfile(Long userId);
}
