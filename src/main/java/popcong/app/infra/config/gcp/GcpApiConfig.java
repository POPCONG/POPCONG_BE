package popcong.app.infra.config.gcp;

import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.ServiceAccountCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class GcpApiConfig {

    private final GcpApiProperties gcpApiProperties;

    @Bean
    public Drive googleDrive() throws IOException, GeneralSecurityException {
        var gsonFactory = GsonFactory.getDefaultInstance();

        var credentials = ServiceAccountCredentials.fromStream(
                new ClassPathResource("popcong-c9addd5f509d.json")
                        .getInputStream()
        ).createScoped(
                List.of(DriveScopes.DRIVE_FILE, DriveScopes.DRIVE_METADATA)
        );

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                gsonFactory,
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName("popcong").build();
    }
}
