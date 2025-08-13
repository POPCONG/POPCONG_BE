package popcong.app.adapter.in.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import popcong.app.global.exception.ErrorResponse;
import popcong.app.global.exception.error.AuthErrorCode;

import java.io.IOException;

/**
 * 인증(Authentication) 실패 처리 (401)
 */
@Component
public class AuthenticationFailureHandler extends AbstractSecurityFailureHandler implements AuthenticationEntryPoint {

    public AuthenticationFailureHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        writeErrorResponse(response, ErrorResponse.of(AuthErrorCode.UNAUTHORIZED));
    }
}