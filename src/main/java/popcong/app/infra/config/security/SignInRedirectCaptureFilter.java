package popcong.app.infra.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;

/**
 * redirect 파라미터를 cookie로 보관
 */
@Slf4j
@Component
public class SignInRedirectCaptureFilter extends OncePerRequestFilter {

    private static final Set<String> ALLOW_LIST = Set.of(
            "http://localhost:8080",
            "http://localhost:5137",
            "https://popcong.vercel.app/"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().startsWith("/oauth2/authorization");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {
        String redirect = request.getParameter("redirect");
        if (redirect == null && ALLOW_LIST.stream().anyMatch(redirect::startsWith)) {
            ResponseCookie cookie = ResponseCookie.from("login_redirect", redirect)
                    .httpOnly(true)
                    .secure(false)
                    .sameSite("Lax")
                    .path("/")
                    .maxAge(Duration.ofMinutes(5))
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        }
        chain.doFilter(request, response);
    }
}
