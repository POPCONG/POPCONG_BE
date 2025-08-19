package popcong.app.application.chat.port.out;

import popcong.app.domain.chat.model.OutgoingMessage;

public interface MessagePublishPort {
    void publish(OutgoingMessage message);
}
