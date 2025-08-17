package popcong.app.infra.config.s3;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

@Slf4j
@Configuration
public class S3Config {

    @Value("${cloud.aws.region:us-east-2}")
    private String region;

    @Bean
    public AWSCredentialsProvider awsCredentialsProvider(
            @Value("${cloud.aws.profile:}") String profile) {
        log.info("[S3] Using profile='{}', region='{}'", profile, region);
        return (profile != null && !profile.isBlank())
                ? new ProfileCredentialsProvider(profile)
                : DefaultAWSCredentialsProviderChain.getInstance();
    }

    @Bean
    @Primary
    public AmazonS3 amazonS3(AWSCredentialsProvider creds) {
        log.info("[S3] Creds provider = {}", creds.getClass().getSimpleName());
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(creds)
                .withForceGlobalBucketAccessEnabled(true)
                .build();
    }

    @Bean
    public AWSSecurityTokenService sts(AWSCredentialsProvider creds) {
        return AWSSecurityTokenServiceClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(creds)
                .build();
    }

    // 앱 기동 후 한 번만 실행되며, 현재 STS 호출 주체를 로깅
    @Bean
    @Order(0)
    public ApplicationRunner logCaller(AWSSecurityTokenService sts) {
        return args -> {
            try {
                var me = sts.getCallerIdentity(
                        new com.amazonaws.services.securitytoken.model.GetCallerIdentityRequest());
                log.info("AWS caller identity: account={}, arn={}, userId={}",
                        me.getAccount(), me.getArn(), me.getUserId());
            } catch (Exception e) {
                log.error("Failed to resolve AWS caller identity.", e);
            }
        };

    }
}