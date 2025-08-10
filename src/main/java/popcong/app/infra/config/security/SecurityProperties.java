package popcong.app.infra.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security.path")
public class SecurityProperties {

    /**
     * Spring Security 허용 path 관리
     */
    private List<String> permitAll = new ArrayList<>();
}
