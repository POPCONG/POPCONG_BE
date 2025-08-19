package popcong.app.adapter.in.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import popcong.app.application.chat.dto.request.CreateChatRequestDto;
import popcong.app.application.chat.dto.request.SendMessageRequestDto;
import popcong.app.application.chat.dto.response.*;
import popcong.app.application.chat.port.in.*;
import popcong.app.application.chat.port.out.MessagePort;
import popcong.app.domain.chat.model.Message;
import popcong.app.domain.user.model.User;
import popcong.app.global.dto.ResponseDto;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.AuthErrorCode;
import popcong.app.global.exception.error.SpaceErrorCode;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/chat")
public class ChatController {

    private final ChatRoomUseCase chatRoomUseCase;
    private final ReadMarkerUseCase readMarkerUseCase;
    private final ListMyChatUseCase listMyChatUseCase;
    private final SendMessageUseCase sendMessageUseCase;
    private final MessagePort messagePort;

    // 채팅방 생성
    @PostMapping("/create-chat")
    public ResponseDto<?> createChat(
            @AuthenticationPrincipal User user,
            @RequestBody CreateChatRequestDto request
    ) {
        if (user == null) throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
        if (request == null) throw new BusinessException(SpaceErrorCode.MISSING_OR_INVALID_SPACE_ID);

        CreateChatResponseDto result = chatRoomUseCase.createChat(user.userId(), request.spaceId());

        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "새로운 채팅방이 생성되었습니다.",
                result
        );
    }

    // 읽음 처리
    @PostMapping(value = "/{chatId}/read", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto<?> markAsRead(
            @AuthenticationPrincipal User user,
            @PathVariable Long chatId,
            @RequestBody(required = false) MarkReadDto request
    ) {
        if (user == null) throw new BusinessException(AuthErrorCode.UNAUTHORIZED);

        LocalDateTime lastSeenAt = (request != null) ? request.lastSeenAt() : null;
        readMarkerUseCase.markAsRead(chatId, user.userId(), lastSeenAt);

        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "메시지를 읽었습니다.",
                null
        );
    }

    @GetMapping("/chat-list")
    public ResponseDto<ChatListResponseDto> listMyChats(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        if (user == null) throw new BusinessException(AuthErrorCode.UNAUTHORIZED);

        Pageable pageable = PageRequest.of(page, size);
        ChatListResponseDto data = listMyChatUseCase.listMyChat(user.userId(), pageable);

        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "채팅 목록 불러오기 성공",
                data
        );
    }

    @PostMapping(value = "/{chatId}/messages", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto<SendMessageResponseDto> sendMessage(
            @AuthenticationPrincipal User user,
            @PathVariable Long chatId,
            @RequestBody SendMessageRequestDto request
    ) {
        if (user == null) throw new BusinessException(AuthErrorCode.UNAUTHORIZED);

        SendMessageResponseDto data = sendMessageUseCase.send(chatId, user.userId(), request.message());

        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "메시지 전송 성공",
                data
        );
    }

    @GetMapping("/{chatId}/messages")
    public ResponseDto<Page<MessageDto>> getChatHistory(
            @AuthenticationPrincipal User user,
            @PathVariable Long chatId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        if (user == null) throw new BusinessException(AuthErrorCode.UNAUTHORIZED);

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt").and(Sort.by(Sort.Direction.DESC, "id"))
        );

        Page<Message> result = messagePort.findByChat(chatId, pageable);

        Page<MessageDto> data = result.map(message ->
                new MessageDto(
                        message.messageId(),
                        message.chatId(),
                        message.senderId(),
                        message.content(),
                        message.createdAt()
                )
        );

        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "메시지 조회 성공",
                data
        );
    }
}
