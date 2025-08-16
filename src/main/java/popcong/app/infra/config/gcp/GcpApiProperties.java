package popcong.app.infra.config.gcp;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "google")
public class GcpApiProperties {

    private String hostDriveId;
    private String guestDriveId;

    private String hostRootDriveName;
    private String guestRootDriveName;
}
