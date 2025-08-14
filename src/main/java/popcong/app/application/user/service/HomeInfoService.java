package popcong.app.application.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import popcong.app.application.user.dto.response.HomeUserResponseDto;
import popcong.app.application.user.dto.response.UserResponseDto;
import popcong.app.application.user.port.in.GetHomeInfoUseCase;
import popcong.app.application.user.port.out.LoadMyPopupsPort;
import popcong.app.application.user.port.out.LoadUserPort;
import popcong.app.application.space.dto.response.MyPopupItemResponseDto;
import popcong.app.domain.user.model.User;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.AuthErrorCode;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeInfoService implements GetHomeInfoUseCase {

    private final LoadUserPort loadUserPort;
    private final LoadMyPopupsPort loadMyPopupsPort;

    @Override
    @Transactional(readOnly = true)
    public HomeUserResponseDto getMyHomeInfo(Long userId, int limit, int offset) {
        // 사용자 조회 (없으면 예외)
        User user = loadUserPort.loadUserById(userId)
                .orElseThrow(() -> new BusinessException(AuthErrorCode.UNAUTHORIZED));

        // 내 팝업 조회
        List<MyPopupItemResponseDto> myPopups =
                loadMyPopupsPort.findMyPopups(userId, limit, offset);

        // 응답 DTO 조합
        return HomeUserResponseDto.from(UserResponseDto.from(user), myPopups);
    }

}

