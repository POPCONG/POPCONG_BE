package popcong.app.adapter.in.jwt.handler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import popcong.app.adapter.in.jwt.CustomUserDetails;
import popcong.app.adapter.out.jwt.JwtUtils;
import popcong.app.domain.auth.model.AuthInfo;
import popcong.app.domain.user.model.User;
import popcong.app.domain.user.model.UserRole;
import popcong.app.infra.config.cors.CorsProperties;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

//import static popcong.app.infra.config.security.SignInRedirectCaptureFilter.ALLOW_LIST;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;

    private final CorsProperties corsProperties;

    @Value("${app.frontend.base-url}")
    private String frontendBaseUrl;

    @Value("${app.frontend.path.main}")
    private String mainPath; // home
    @Value("${app.frontend.path.signup}")
    private String signupPath; // signup

    @Value("${app.cookie.domain}")
    private String cookieDomain;
    @Value("${app.cookie.secure}")
    private boolean cookieSecure;
    @Value("${app.cookie.same-site}")
    private String cookieSameSite;
    @Value("${app.cookie.access.name}")
    private String accessCookieName;
    @Value("${app.cookie.access.path}")
    private String accessCookiePath;
    @Value("${app.cookie.access.max-age}")
    private long accessCookieMaxAge;
    @Value("${app.cookie.refresh.name}")
    private String refreshCookieName;
    @Value("${app.cookie.refresh.path}")
    private String refreshCookiePath;
    @Value("${app.cookie.refresh.max-age}")
    private long refreshCookieMaxAge;


    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        // 사용자 정보 불러오기
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        // 토큰 발급
        AuthInfo authInfo = AuthInfo.from(user);
        String accessToken = jwtUtils.generateAccessToken(authInfo);
        String refreshToken = jwtUtils.generateRefreshToken(authInfo);

        // 회원 여부 확인
        boolean isRegistered = !UserRole.GUEST.equals(user.userRole());

        log.info("OAuth2 로그인 처리 완료 - email: {}, isRegistered: {}", user.email(), isRegistered);
        log.info("accessToken: {}", accessToken);
        log.info("refreshToken: {}", refreshToken);

        // 이름 모두 제거
        removeCookie(response, "access_token", "/");
        removeCookie(response, "refresh_token", "/");
        removeCookie(response, accessCookieName, accessCookiePath);
        removeCookie(response, refreshCookieName, refreshCookiePath);

        // 현재 이름으로만 심기
        addCookie(response, accessCookieName, accessToken, accessCookiePath, accessCookieMaxAge);
        addCookie(response, refreshCookieName, refreshToken, refreshCookiePath, refreshCookieMaxAge);

        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");

        String desired = readCookie(request, "login_redirect")
                .or(() -> Optional.ofNullable(request.getParameter("redirect_uri")))
                .or(() -> Optional.ofNullable(request.getHeader("Origin")))
                .orElse(null);

        String base = resolveAllowedBase(desired, frontendBaseUrl);
        String redirectUrl = base + (isRegistered ? mainPath : signupPath);

        removeCookie(response, "login_redirect", "/");

        log.info("리다이렉트 URL: {}", redirectUrl);
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private void addCookie(
            HttpServletResponse response,
            String name,
            String value,
            String path,
            long maxAgeSeconds
    ) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite(cookieSameSite)
                .domain(cookieDomain)
                .path(path)
                .maxAge(Duration.ofSeconds(maxAgeSeconds))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private void removeCookie(HttpServletResponse response, String name, String path) {
        ResponseCookie cookie = ResponseCookie.from(name, "")
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite(cookieSameSite)
                .domain(cookieDomain)
                .path(path)
                .maxAge(Duration.ZERO) // 삭제
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private Optional<String> readCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return Optional.empty();
        return Arrays.stream(cookies)
                .filter(c -> name.equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }


    private String resolveAllowedBase(String desired, String fallback) {
        if (desired == null || desired.isBlank()) {
            return fallback;
        }
        boolean allowed = corsProperties.getAllowedOrigins().stream().anyMatch(desired::startsWith);
        return allowed ? desired : fallback;
    }
}