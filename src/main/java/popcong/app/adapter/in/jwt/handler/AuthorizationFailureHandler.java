package popcong.app.adapter.in.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import popcong.app.global.exception.ErrorResponse;
import popcong.app.global.exception.error.AuthErrorCode;

import java.io.IOException;

/**
 * 인가(Authorization) 실패 처리 (403)
 */
@Slf4j
@Component
public class AuthorizationFailureHandler extends AuthenticationFailureHandler implements AccessDeniedHandler {

    public AuthorizationFailureHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        log.error("No Authorities", accessDeniedException);
        writeErrorResponse(response, ErrorResponse.of(AuthErrorCode.ACCESS_DENIED));
    }
}