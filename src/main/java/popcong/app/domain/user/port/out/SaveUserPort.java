package popcong.app.domain.user.port.out;

import popcong.app.domain.user.model.User;

public interface SaveUserPort {
    User saveUser(User user);
}
