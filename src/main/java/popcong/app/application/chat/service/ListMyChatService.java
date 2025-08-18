package popcong.app.application.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import popcong.app.adapter.out.persistence.chat.entity.ChatJpaEntity;
import popcong.app.adapter.out.persistence.chat.entity.MessageJpaEntity;
import popcong.app.adapter.out.persistence.chat.repository.ChatJpaRepository;
import popcong.app.adapter.out.persistence.chat.repository.MessageJpaRepository;
import popcong.app.application.chat.dto.response.ChatInfoDto;
import popcong.app.application.chat.dto.response.ChatListItemDto;
import popcong.app.application.chat.dto.response.ChatListResponseDto;
import popcong.app.application.chat.dto.response.PartnerInfoDto;
import popcong.app.application.chat.port.in.ListMyChatUseCase;
import popcong.app.domain.user.model.UserRole;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListMyChatService implements ListMyChatUseCase {

    private final ChatJpaRepository chatJpaRepository;
    private final MessageJpaRepository messageJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public ChatListResponseDto listMyChat(Long userId, Pageable pageable) {

        Page<ChatJpaEntity> page = chatJpaRepository
                .findByGuest_UserIdOrHost_UserIdOrderByLastMessageAtDesc(userId, userId, pageable);

        List<ChatListItemDto> items = new ArrayList<>();

        for (ChatJpaEntity entity : page.getContent()) {
            boolean iAmGuest = entity.getGuest().getUserId().equals(userId);
            boolean iAmHost = entity.getHost().getUserId().equals(userId);

            if (!iAmGuest && !iAmHost) continue;


            Long partnerId = iAmGuest ? entity.getHost().getUserId() : entity.getGuest().getUserId();
            String partnerName = iAmGuest ? entity.getHost().getName() : entity.getGuest().getName();
            String partnerImage = iAmGuest ? entity.getHost().getProfileImageUrl() : entity.getGuest().getProfileImageUrl();

            PartnerInfoDto partnerInfo = new PartnerInfoDto(partnerId, partnerName, partnerImage);

            String lastMessage = messageJpaRepository.findTopByChat_ChatIdOrderByCreatedAtDesc(entity.getChatId())
                    .map(MessageJpaEntity::getContent)
                    .orElse(null);

            LocalDateTime myLastReadAt = iAmGuest ? entity.getGuestLastReadAt() : entity.getHostLastReadAt();

            long unreadCount = messageJpaRepository.countByChat_ChatIdAndSender_UserIdAndCreatedAtAfter(
                    entity.getChatId(), partnerId, myLastReadAt
            );

            ChatInfoDto chatInfo = new ChatInfoDto(
                    entity.getChatId(),
                    lastMessage,
                    entity.getCreatedAt(),
                    entity.getLastMessageAt(),
                    myLastReadAt,
                    unreadCount
            );

            items.add(new ChatListItemDto(chatInfo, partnerInfo));
        }

        UserRole userRole = UserRole.GENERAL;
        if (!items.isEmpty()) {
            ChatListItemDto first = items.get(0);
            ChatJpaEntity firstChat = page.getContent().get(0);
            userRole = firstChat.getGuest().getUserId().equals(userId) ? UserRole.GENERAL : UserRole.HOST;
        }

        return new ChatListResponseDto(userRole, items);
    }
}
