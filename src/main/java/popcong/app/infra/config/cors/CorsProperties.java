package popcong.app.infra.config.cors;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {

    /**
     * 허용된 Origins
     */
    private List<String> allowedOrigins = new ArrayList<>();

    /**
     * 허용된 HTTP Methods
     */
    private List<String> allowedMethods = new ArrayList<>();
}