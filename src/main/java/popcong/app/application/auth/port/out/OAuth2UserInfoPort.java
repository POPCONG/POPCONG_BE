package popcong.app.application.auth.port.out;

import popcong.app.domain.user.model.Provider;

/**
 * 카카오에서 제공하는 attributes 에서 정보 가져오기
 */
public interface OAuth2UserInfoPort {
    Provider getProvider();
    String getProviderId();
    String getEmail();
}
