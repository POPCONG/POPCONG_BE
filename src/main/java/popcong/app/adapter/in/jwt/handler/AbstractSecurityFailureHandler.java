package popcong.app.adapter.in.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import popcong.app.global.exception.ErrorResponse;

import java.io.IOException;

/**
 * 보안 예외 응답 공통 처리
 */
@RequiredArgsConstructor
public abstract class AbstractSecurityFailureHandler {

    private final ObjectMapper objectMapper;

    protected void writeErrorResponse(
            HttpServletResponse response,
            ErrorResponse errorResponse
    ) throws IOException {
        response.setStatus(errorResponse.getHttpStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
