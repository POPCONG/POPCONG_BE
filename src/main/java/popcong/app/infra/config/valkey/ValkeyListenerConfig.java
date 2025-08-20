package popcong.app.infra.config.valkey;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import popcong.app.adapter.out.valkey.ValkeySubscriber;

import java.util.concurrent.Executors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ValkeyListenerConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        // ❌ PatternTopic("chat:*") 절대 등록하지 말 것 (PSUBSCRIBE 발생)
        // container.addMessageListener(valkeySubscriber, new PatternTopic("chat:*"));

        // 권장: 에러 핸들러/스레드 설정(옵션)
        container.setErrorHandler(e -> log.warn("Redis listener error", e));
        container.setTaskExecutor(Executors.newFixedThreadPool(2));
        container.setSubscriptionExecutor(Executors.newSingleThreadExecutor());
        return container;
    }
}