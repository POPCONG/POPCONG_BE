package popcong.app.adapter.out.persistence.chat.mapper;

import org.springframework.stereotype.Component;
import popcong.app.adapter.out.persistence.chat.entity.MessageJpaEntity;
import popcong.app.domain.chat.model.Message;

@Component
public class MessageMapper {

    public Message toDomain(MessageJpaEntity entity) {
        return new Message(
                entity.getId(),
                entity.getChat().getChatId(),
                entity.getSender().getUserId(),
                entity.getContent(),
                entity.getCreatedAt()
        );
    }
}
