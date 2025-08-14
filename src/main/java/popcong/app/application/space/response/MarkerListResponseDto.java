package popcong.app.application.space.response;

import java.util.List;

public record MarkerListResponseDto(
        List<MarkerComponentDto> markers
) {}