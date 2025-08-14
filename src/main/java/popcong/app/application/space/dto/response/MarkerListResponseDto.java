package popcong.app.application.space.dto.response;

import java.util.List;

public record MarkerListResponseDto(
        List<MarkerComponentDto> markers
) {}