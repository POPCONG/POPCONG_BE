package popcong.app.infra.config.valkey;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import popcong.app.adapter.out.valkey.ValkeySubscriber;

@Configuration
@RequiredArgsConstructor
public class ValkeyListenerConfig {

    private final RedisConnectionFactory redisConnectionFactory;
    private final ValkeySubscriber valkeySubscriber;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(valkeySubscriber, new PatternTopic("chat:*"));

        return container;
    }
}
