package popcong.app.adapter.out.persistence.space;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import popcong.app.adapter.out.persistence.space.repository.SpaceJpaRepository;
import popcong.app.application.user.port.out.SpaceLookupPort;


//Space의 존재를 확인하는 어댑터
@Component
@RequiredArgsConstructor
public class SpaceLookupAdapter implements SpaceLookupPort {

    private final SpaceJpaRepository spaceJpaRepository;

    @Override
    public boolean existsById(Long spaceId) {
        return spaceJpaRepository.existsById(spaceId);
    }
}