package popcong.app.application.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import popcong.app.application.image.service.ProfileImageService;
import popcong.app.application.user.port.in.UpdateMyProfileUseCase;
import popcong.app.application.user.port.out.UpdateUserPort;
import popcong.app.domain.user.model.UserRole;

@Service
@RequiredArgsConstructor
public class UpdateMyProfileService implements UpdateMyProfileUseCase {

    private final UpdateUserPort updateUserPort;
    private final ProfileImageService profileImageService;

    /** name/introduction/profileImageUrl 각각 null 이면 “해당 항목은 변경 없음” 처리 */
    @Transactional
    @Override
    public void update(Long userId, String name, String introduction, String profileImageUrl) {
        // 이름/소개만 업데이트
        updateUserPort.updateProfile(userId, name, introduction, profileImageUrl);

        // 프로필 이미지는 ProfileImageService로 위임
        if (profileImageUrl != null) {
            profileImageService.replaceProfileImage(userId, profileImageUrl);
        }
    }
}