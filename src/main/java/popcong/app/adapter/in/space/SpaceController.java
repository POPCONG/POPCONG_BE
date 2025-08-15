package popcong.app.adapter.in.space;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import popcong.app.adapter.out.persistence.space.mapper.SpaceMarkerMapper;
import popcong.app.application.space.dto.request.CurrentUserLocationRequestDto;
import popcong.app.application.space.dto.response.MarkerComponentDto;
import popcong.app.application.space.dto.response.MarkerListResponseDto;
import popcong.app.application.space.port.in.SpaceMapQueryUseCase;
import popcong.app.domain.space.model.Space;
import popcong.app.domain.space.model.SpaceSortType;
import popcong.app.global.dto.ResponseDto;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.SpaceErrorCode;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/popup/space")
public class SpaceController {

    private final SpaceMapQueryUseCase spaceMapQueryUseCase;
    private final SpaceMarkerMapper spaceMarkerMapper;

    @GetMapping("/markers")
    public ResponseDto<MarkerListResponseDto> getMarkers(
            @RequestBody CurrentUserLocationRequestDto request,
            @RequestParam(name = "nw-lat") Double nwLat,
            @RequestParam(name = "nw-lng") Double nwLng,
            @RequestParam(name = "se-lat") Double seLat,
            @RequestParam(name = "se-lng") Double seLng,
            @RequestParam(name = "min-amount", required = false) Integer minAmount,
            @RequestParam(name = "max-amount", required = false) Integer maxAmount,
            @RequestParam(name = "floor", required = false) Integer floor,
            @RequestParam(name = "rating", required = false) Double rating,
            @RequestParam(name = "sort", defaultValue = "MOST_POPULAR") SpaceSortType sort
    ){
        if (minAmount != null && maxAmount != null && minAmount > maxAmount) {
            throw new BusinessException(SpaceErrorCode.INVALID_PRICE_ERROR);
        }

        List<Space> spaces = spaceMapQueryUseCase.getSpaceMarkersInDisplayWithFilters(
                nwLat, nwLng, seLat, seLng,
                minAmount, maxAmount, floor, rating, sort
        );

        List<MarkerComponentDto> markers = spaces.stream()
                .map(spaceMarkerMapper::toMarkerDto)
                .toList();

        MarkerListResponseDto result = new MarkerListResponseDto(markers);

        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "마커 조회 성공",
                result
        );
    }
}
