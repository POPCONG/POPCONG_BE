package popcong.app.adapter.out.persistence.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import popcong.app.adapter.out.persistence.user.Mapper.UserMapper;
import popcong.app.adapter.out.persistence.user.entity.UserJpaEntity;
import popcong.app.domain.user.model.User;
import popcong.app.domain.user.port.out.LoadUserPort;
import popcong.app.domain.user.port.out.SaveUserPort;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserAdapter implements LoadUserPort, SaveUserPort {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> loadUserByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public User saveUser(User user) {
        UserJpaEntity entity = userJpaRepository.save(userMapper.toEntity(user));
        return userMapper.toDomain(entity);
    }
}
