package popcong.app.application.auth.service;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import popcong.app.domain.auth.model.AuthInfo;
import popcong.app.domain.auth.port.in.TokenGenerateUseCase;
import popcong.app.infra.config.jwt.JwtProperties;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AccessTokenGenerateService implements TokenGenerateUseCase {

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    /**
     * AuthInfo의 사용자 id, providerId, email, role, userRole로 Access Token 생성
     * @param authInfo
     * @return accessToken 발급
     */
    @Override
    public String generateToken(AuthInfo authInfo) {
        Date now = new Date();
        Long expiration = jwtProperties.getExpirationTime().getAccessToken();

        return Jwts.builder()
                .setSubject(authInfo.id().toString())
                .claim("email", authInfo.email())
                .claim("role", authInfo.role())
                .claim("tokenType", "ACCESS")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .signWith(secretKey)
                .compact();
    }
}