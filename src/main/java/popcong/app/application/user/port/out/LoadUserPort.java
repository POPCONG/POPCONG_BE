package popcong.app.application.user.port.out;

import popcong.app.domain.user.model.User;

import java.util.Optional;

public interface LoadUserPort {
    Optional<User> loadUserByEmail(String email);
    Optional<User> loadUserById(Long id);
}