package popcong.app.adapter.out.oauth;

import lombok.Getter;
import popcong.app.domain.user.model.Provider;

import java.util.Map;

@Getter
public class OAuthUserInfoFactory {

    public static OAuthUserInfoFactory of(
            String registrationId,
            Map<String, Object> attributes
    ) {
        if (Provider.kakao.toString().equals(registrationId)) {
            return new Kakao
        }
    }
}
