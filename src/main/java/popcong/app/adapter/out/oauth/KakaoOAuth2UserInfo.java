package popcong.app.adapter.out.oauth;

import popcong.app.application.auth.port.out.OAuth2UserInfoPort;
import popcong.app.domain.user.model.Provider;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.AuthErrorCode;

import java.util.Map;

public class KakaoOAuth2UserInfo implements OAuth2UserInfoPort {

    private final Map<String, Object> attributes;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public Provider getProvider() {
        return Provider.kakao;
    }

    @Override
    public String getEmail() {
        Object kakaoAccountObject = attributes.get("kakao_account");

        if (!(kakaoAccountObject instanceof Map)) {
            throw new BusinessException(AuthErrorCode.INVALID_KAKAO_RESPONSE);
        }

        Map<String, Object> kakaoAccount = (Map<String, Object>) kakaoAccountObject;

        return (String) kakaoAccount.get("email");
    }
}