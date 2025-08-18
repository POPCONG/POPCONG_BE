package popcong.app.adapter.out.persistence.chat.mapper;

import org.springframework.stereotype.Component;
import popcong.app.adapter.out.persistence.chat.entity.ChatJpaEntity;
import popcong.app.domain.chat.model.Chat;

@Component
public class ChatMapper {

    public static Chat toDomain(ChatJpaEntity entity) {
        return new Chat(
                entity.getChatId(),
                entity.getGuest().getUserId(),
                entity.getHost().getUserId(),
                entity.getSpace().getSpaceId(),
                entity.getCreatedAt(),
                entity.getLastMessageAt(),
                entity.getLastReadAt()
        );
    }
}