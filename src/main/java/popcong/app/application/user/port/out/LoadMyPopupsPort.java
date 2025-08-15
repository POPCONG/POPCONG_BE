package popcong.app.application.user.port.out;

import popcong.app.application.space.dto.response.MyPopupItemResponseDto;

import java.util.List;

public interface LoadMyPopupsPort {
    List<MyPopupItemResponseDto> findMyPopups(Long userId);
}
