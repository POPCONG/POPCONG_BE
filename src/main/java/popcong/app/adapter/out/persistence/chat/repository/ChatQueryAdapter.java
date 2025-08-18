package popcong.app.adapter.out.persistence.chat.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import popcong.app.adapter.out.persistence.chat.entity.ChatJpaEntity;
import popcong.app.adapter.out.persistence.chat.mapper.ChatMapper;
import popcong.app.adapter.out.persistence.space.entity.SpaceJpaEntity;
import popcong.app.adapter.out.persistence.space.mapper.SpaceMapper;
import popcong.app.adapter.out.persistence.space.repository.SpaceJpaRepository;
import popcong.app.adapter.out.persistence.user.Mapper.UserMapper;
import popcong.app.adapter.out.persistence.user.entity.UserJpaEntity;
import popcong.app.adapter.out.persistence.user.repository.UserJpaRepository;
import popcong.app.application.chat.port.out.ChatRoomPort;
import popcong.app.domain.chat.model.Chat;
import popcong.app.domain.space.model.Space;
import popcong.app.domain.user.model.User;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
@Transactional
public class ChatQueryAdapter implements ChatRoomPort {

    private final ChatJpaRepository chatJpaRepository;
    private final SpaceJpaRepository spaceJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final EntityManager entityManager;

    private final ChatMapper chatMapper;
    private final SpaceMapper spaceMapper;
    private final UserMapper userMapper;


    @Override
    public Chat save(Chat chat) {
        UserJpaEntity guestReference = entityManager.getReference(UserJpaEntity.class, chat.guestId());
        UserJpaEntity hostReference = entityManager.getReference(UserJpaEntity.class, chat.hostId());
        SpaceJpaEntity spaceReference = entityManager.getReference(SpaceJpaEntity.class, chat.spaceId());

        ChatJpaEntity entity = ChatJpaEntity.builder()
                .guest(guestReference)
                .host(hostReference)
                .space(spaceReference)
                .createdAt(chat.createdAt())
                .lastMessageAt(chat.lastMessageAt())
                .lastReadAt(chat.lastReadAt())
                .guestLastReadAt(chat.guestLastReadAt())
                .hostLastReadAt(chat.hostLastReadAt())
                .build();

        ChatJpaEntity saved = chatJpaRepository.save(entity);
        return chatMapper.toDomain(saved);
    };

    @Override
    public Optional<Long> findHostIdBySpaceId(Long spaceId) {
        return spaceJpaRepository.findById(spaceId)
                .map(spaceMapper::toDomain)
                .map(Space::userId);
    };

    public Optional<Chat> findExistingChat(Long guestId, Long hostId, Long spaceId) {
        return chatJpaRepository.findByGuest_UserIdAndHost_UserIdAndSpace_SpaceId(guestId, hostId, spaceId)
                .map(chatMapper::toDomain);
    };

    public Optional<Space> findSpaceById(Long spaceId) {
        return spaceJpaRepository.findById(spaceId)
                .map(spaceMapper::toDomain);
    };

    public Optional<User> findUserById(Long userId) {
        return userJpaRepository.findById(userId)
                .map(userMapper::toDomain);
    };

}
