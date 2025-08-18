package popcong.app.infra.config.chat;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ChatChannels {

    public String chat(Long chatId) {
        return "chat:" +  chatId;
    }
}
