package popcong.app.adapter.in.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import popcong.app.application.chat.dto.request.CreateChatRequestDto;
import popcong.app.application.chat.dto.response.CreateChatResponseDto;
import popcong.app.application.chat.port.in.ChatRoomUseCase;
import popcong.app.domain.user.model.User;
import popcong.app.global.dto.ResponseDto;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.AuthErrorCode;
import popcong.app.global.exception.error.SpaceErrorCode;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/chat")
public class ChatController {

    private final ChatRoomUseCase chatRoomUseCase;

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
}
