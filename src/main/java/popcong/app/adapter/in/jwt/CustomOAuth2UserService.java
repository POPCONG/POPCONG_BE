package popcong.app.adapter.in.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import popcong.app.adapter.out.oauth.OAuth2UserInfoFactory;
import popcong.app.domain.auth.model.OAuth2SignInCommand;
import popcong.app.application.auth.port.in.OAuth2SignInUseCase;
import popcong.app.application.auth.port.out.OAuth2UserInfoPort;
import popcong.app.domain.user.model.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final OAuth2SignInUseCase oAuth2SignInUseCase;

    // 카카오 응답 시 User 매핑
    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        log.debug("CustomOAuth2UserService loadUser");

        OAuth2User oAuth2User = super.loadUser(request);
        log.debug("kakao 응답 : {}", oAuth2User.getAttributes());

        String registrationId = request.getClientRegistration().getRegistrationId();

        // OAuth2UserInfoFactory로 사용자 객체 변환
        OAuth2UserInfoPort userInfo = OAuth2UserInfoFactory.of(registrationId, oAuth2User.getAttributes());

        log.debug("email = {}, providerId = {}", userInfo.getEmail(), userInfo.getProviderId());

        // command 객체로 변환
        OAuth2SignInCommand command = new OAuth2SignInCommand(
                userInfo.getProvider(),
                userInfo.getProviderId(),
                userInfo.getEmail()
        );

        User user = oAuth2SignInUseCase.findOrCreateUser(command);

        return new CustomUserDetails(user, oAuth2User.getAttributes());
    }
}
