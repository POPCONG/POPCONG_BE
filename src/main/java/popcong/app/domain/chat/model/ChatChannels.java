package popcong.app.domain.chat.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ChatChannels {

    public String chat(Long chatId) {
        return "chat:" +  chatId;
    }
}
