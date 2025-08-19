package popcong.app.adapter.out.persistence.chat.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import popcong.app.adapter.out.persistence.chat.entity.ChatJpaEntity;
import popcong.app.adapter.out.persistence.space.mapper.SpaceMapper;
import popcong.app.adapter.out.persistence.user.Mapper.UserMapper;
import popcong.app.domain.chat.model.Chat;

@Component
public class ChatMapper {

    public Chat toDomain(ChatJpaEntity entity) {
        if (entity == null) return null;

        Long guestId = entity.getGuest() != null ? entity.getGuest().getUserId() : null;
        Long hostId = entity.getHost() != null ? entity.getHost().getUserId() : null;
        Long spaceId = entity.getSpace() != null ? entity.getSpace().getSpaceId() : null;

        return new Chat(
                entity.getChatId(),
                guestId,
                hostId,
                spaceId,
                entity.getCreatedAt(),
                entity.getLastMessageAt(),
                entity.getLastReadAt(),
                entity.getGuestLastReadAt(),
                entity.getHostLastReadAt()
        );
    }
}