package popcong.app.application.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import popcong.app.application.user.port.in.GetMyProfileUseCase;
import popcong.app.application.user.port.out.LoadUserPort;
import popcong.app.domain.user.model.User;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.AuthErrorCode;

@Service
@RequiredArgsConstructor
public class GetMyProfileService implements GetMyProfileUseCase {

    private final LoadUserPort loadUserPort;

    @Override
    public User getMyProfile(Long userId) {
        return loadUserPort.loadUserById(userId)
                .orElseThrow(() -> new BusinessException(AuthErrorCode.USER_NOT_FOUND));
    }
}
