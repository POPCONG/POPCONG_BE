package popcong.app.adapter.out.valkey;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import popcong.app.domain.chat.model.OutgoingMessage;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValkeySubscriber implements MessageListener {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message redisMessage, byte[] pattern) {
        try {
            String json = new String(redisMessage.getBody(), StandardCharsets.UTF_8);
            OutgoingMessage outgoingMessage = objectMapper.readValue(json, OutgoingMessage.class);

            simpMessagingTemplate.convertAndSend("/topic/chat." + outgoingMessage.chatId(), outgoingMessage);
        } catch (Exception ignore) {
            log.warn(ignore.getMessage(), ignore);
        }
    }
}
