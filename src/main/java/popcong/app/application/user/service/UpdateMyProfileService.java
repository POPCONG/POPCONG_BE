package popcong.app.application.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import popcong.app.application.user.port.in.UpdateMyProfileUseCase;
import popcong.app.application.user.port.out.UpdateUserPort;

@Service
@RequiredArgsConstructor
public class UpdateMyProfileService implements UpdateMyProfileUseCase {

    private final UpdateUserPort updateUserPort;

    /** name/introduction/profileImageUrl 각각 null 이면 “해당 항목은 변경 없음” 처리 */
    @Override
    @Transactional
    public void update(Long userId, String name, String introduction, String profileImageUrl) {
        updateUserPort.updateProfile(userId, name, introduction, profileImageUrl);
    }
}