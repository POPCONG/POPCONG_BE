package popcong.app.application.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import popcong.app.application.chat.dto.response.ChatRoomInfoDto;
import popcong.app.application.chat.dto.response.CreateChatResponseDto;
import popcong.app.application.chat.dto.response.ParticipantInfoDto;
import popcong.app.application.chat.port.in.ChatRoomUseCase;
import popcong.app.application.chat.port.out.ChatRoomPort;
import popcong.app.application.image.port.in.ImageQueryUseCase;
import popcong.app.domain.chat.model.Chat;
import popcong.app.domain.image.model.ImageableType;
import popcong.app.domain.space.model.Space;
import popcong.app.domain.user.model.User;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.ChatErrorCode;
import popcong.app.global.exception.error.SpaceErrorCode;
import popcong.app.global.exception.error.UserErrorCode;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService implements ChatRoomUseCase {

    private final ChatRoomPort chatRoomPort;
    private final ImageQueryUseCase imageQueryUseCase;

    @Override
    public CreateChatResponseDto createChat(Long userId, Long spaceId) {

        if (userId == null || spaceId == null) {
            throw new BusinessException(ChatErrorCode.INVALID_CHAT_ROOM);
        }

        Long hostId = chatRoomPort.findHostIdBySpaceId(spaceId)
                .orElseThrow(() -> new BusinessException(SpaceErrorCode.SPACE_NOT_FOUND));

        if (hostId.equals(userId)) {
            throw new BusinessException(ChatErrorCode.SELF_CHAT_NOT_ALLOWED);
        }

        Optional<Chat> existing = chatRoomPort.findExistingChat(userId, hostId, spaceId);
        Chat chat;

        if (existing.isPresent()) {
            chat = existing.get();
        } else {
            LocalDateTime now = LocalDateTime.now(Clock.system(ZoneId.of("Asia/Seoul")));
            Chat toSave = new Chat(null, userId, hostId, spaceId, now, now, now);

            try {
                chat = chatRoomPort.save(toSave);
            } catch (DataIntegrityViolationException e) {
                chat = chatRoomPort.findExistingChat(userId, hostId, spaceId)
                        .orElseThrow(() -> e);
            }
        }

        Space space = chatRoomPort.findSpaceById(chat.spaceId())
                .orElseThrow(() -> new BusinessException(SpaceErrorCode.SPACE_NOT_FOUND));

        User guest = chatRoomPort.findUserById(chat.guestId())
                .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));

        User host = chatRoomPort.findUserById(chat.hostId())
                .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));

        String coverImage = imageQueryUseCase.getCoverImageUrl(
                ImageableType.SPACE, spaceId
        );

        ChatRoomInfoDto chatRoom = new ChatRoomInfoDto(
                chat.chatId(),
                space.spaceName(),
                space.address(),
                space.floor(),
                space.rentalFee(),
                coverImage,
                chat.createdAt(),
                space.maxPeriod()
        );

        ParticipantInfoDto guestInfo = new ParticipantInfoDto(guest.userId(), guest.name());
        ParticipantInfoDto hostInfo = new ParticipantInfoDto(host.userId(), host.name());

        return new CreateChatResponseDto(chatRoom, guestInfo, hostInfo);
    }
}
