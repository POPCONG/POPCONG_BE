package popcong.app.adapter.in.jwt.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import popcong.app.adapter.in.jwt.CustomUserDetails;
import popcong.app.adapter.out.jwt.JwtUtils;
import popcong.app.domain.auth.model.AuthInfo;
import popcong.app.domain.user.model.User;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws ServletException, IOException {

        // 사용자 정보 불러오기
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        // AuthInfo 생성
        AuthInfo authInfo = AuthInfo.from(user);

        // JWT 발급
        String accessToken = jwtUtils.generateAccessToken(authInfo);
        String refreshToken = jwtUtils.generateRefreshToken(authInfo);

        log.info("로그인 성공");
        log.info("accessToken: {}", accessToken);
        log.info("refreshToken: {}", refreshToken);

        // RedirectUrl 생성 (frontend)
        String redirectUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/swagger-ui.html")
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build()
                .toUriString();

        // redirect
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}