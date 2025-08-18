package popcong.app.application.space.dto.response;

import java.util.List;

public record PopularSpacesDto(
        List<PopularSpaceInfoDto> popularSpaces
) {}