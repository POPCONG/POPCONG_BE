package popcong.app.infra.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import popcong.app.adapter.in.jwt.CustomOAuth2UserService;
import popcong.app.adapter.in.jwt.JwtFilter;
import popcong.app.adapter.in.jwt.handler.AuthenticationFailureHandler;
import popcong.app.adapter.in.jwt.handler.AuthorizationFailureHandler;
import popcong.app.adapter.in.jwt.handler.OAuth2SuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityProperties securityProperties;

    private final JwtFilter jwtFilter;

    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final AuthorizationFailureHandler authorizationFailureHandler;

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private final SignInRedirectCaptureFilter signInRedirectCaptureFilter;

    /**
     * SpringSecurity 보안 규칙 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                // 세션 상태 필요 (oauth2)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                // redirect 파라미터 쿠키 저장
                .addFilterBefore(signInRedirectCaptureFilter, UsernamePasswordAuthenticationFilter.class)
                // 필터 체인에 커스텀 필터 추가
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                // 요청 별 접근 권한 설정
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(securityProperties.getPermitAll().toArray(new String[0]))
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                // security exception 핸들러
                .exceptionHandling(exceptions ->
                        exceptions.authenticationEntryPoint(authenticationFailureHandler)
                                .accessDeniedHandler(authorizationFailureHandler)
                )
                .oauth2Login(oauth2 ->
                        oauth2.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                                .successHandler(oAuth2SuccessHandler)
                );
        ;

        return http.build();
    }
}