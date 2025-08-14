package popcong.app.application.user.port.out;

import popcong.app.application.space.dto.response.MyPopupItemResponseDto;

import java.util.List;

public interface LoadMyPopupsPort {
    List<MyPopupItemResponseDto> findMyPopups(Long userId, int limit, int offset);
    //limit,offset -> 페이징
}
