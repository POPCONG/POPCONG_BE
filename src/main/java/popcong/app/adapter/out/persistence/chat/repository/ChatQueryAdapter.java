package popcong.app.adapter.out.persistence.chat.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import popcong.app.adapter.out.persistence.space.repository.SpaceJpaRepository;
import popcong.app.adapter.out.persistence.user.repository.UserJpaRepository;
import popcong.app.application.chat.port.out.ChatRoomPort;
import popcong.app.domain.chat.model.Chat;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class ChatQueryAdapter implements ChatRoomPort {

    private final ChatJpaRepository chatJpaRepository;
    private final SpaceJpaRepository spaceJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final EntityManager entityManager;

    public Chat save(Chat chat) {};

    public Optional<Long> findHostIdBySpaceId(Long spaceId) {};

    public Optional<Chat> findExistingChat(Long guestId, Long hostId, Long roomId){};

}
