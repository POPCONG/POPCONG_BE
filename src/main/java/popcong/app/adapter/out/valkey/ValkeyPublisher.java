package popcong.app.adapter.out.valkey;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import popcong.app.application.chat.port.out.MessagePublishPort;
import popcong.app.domain.chat.model.OutgoingMessage;
import popcong.app.domain.chat.model.ChatChannels;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValkeyPublisher implements MessagePublishPort {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;


    @Override
    public void publish(OutgoingMessage message) {
        String channel = ChatChannels.chat(message.chatId());

        try {
            String payload = objectMapper.writeValueAsString(message);
            stringRedisTemplate.convertAndSend(channel, payload);
        } catch (JsonProcessingException e) {
            log.warn("메시지 전송 실패!", e);
        }
    }
}
