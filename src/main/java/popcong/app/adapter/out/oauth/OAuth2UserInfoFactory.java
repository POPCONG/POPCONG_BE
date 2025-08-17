package popcong.app.adapter.out.oauth;

import lombok.Getter;
import popcong.app.application.auth.port.out.OAuth2UserInfoPort;
import popcong.app.domain.user.model.Provider;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.AuthErrorCode;

import java.util.Map;

@Getter
public class OAuth2UserInfoFactory {

    public static OAuth2UserInfoPort of(
            String registrationId,
            Map<String, Object> attributes
    ) {
        if (Provider.kakao.toString().equals(registrationId)) {
            return new KakaoOAuth2UserInfo(attributes);
        }

        // google, naver, apple 확장 가능

        throw new BusinessException(AuthErrorCode.UNSUPPORTED_SOCIAL_LOGIN);
    }
}
