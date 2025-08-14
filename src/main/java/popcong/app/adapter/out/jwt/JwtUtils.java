package popcong.app.adapter.out.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import popcong.app.domain.auth.model.AuthInfo;
import popcong.app.application.auth.port.in.TokenGenerateUseCase;

import javax.crypto.SecretKey;
import java.util.Collections;

@Slf4j
@Component
public class JwtUtils {

    private final SecretKey secretKey;
    public final TokenGenerateUseCase accessTokenGenerateService;
    public final TokenGenerateUseCase refreshTokenGenerateService;

    public JwtUtils(
            SecretKey secretKey,
            @Qualifier("accessTokenGenerateService") TokenGenerateUseCase accessTokenGenerateService,
            @Qualifier("refreshTokenGenerateService")  TokenGenerateUseCase refreshTokenGenerateService
    ) {
        this.secretKey = secretKey;
        this.accessTokenGenerateService = accessTokenGenerateService;
        this.refreshTokenGenerateService = refreshTokenGenerateService;
    }

    // Access Tokne 생성
    public String generateAccessToken(AuthInfo authInfo) {
        return accessTokenGenerateService.generateToken(authInfo);
    }

    // Refresh Token 생성
    public String generateRefreshToken(AuthInfo authInfo) {
        return refreshTokenGenerateService.generateToken(authInfo);
    }

    // Token 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 서명입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }

        return false;
    }

    // JWT Claims 추출
    private Claims getClaimsFromToken(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // Access Token에서 인증 정보 추출
    public Authentication getAuthentication(String accessToken) {
        Claims claims = getClaimsFromToken(accessToken);
        String role = claims.get("role", String.class);

        User principal = new User(
                claims.getSubject(),
                "",
                Collections.singleton(new SimpleGrantedAuthority(role))
        );

        return new UsernamePasswordAuthenticationToken(
                principal,
                accessToken,
                principal.getAuthorities()
        );
    }
}
