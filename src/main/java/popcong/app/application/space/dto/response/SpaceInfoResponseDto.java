package popcong.app.application.space.dto.response;

public record SpaceInfoResponseDto(
        Long spaceId,
        String spaceName,
        String floor,
        String address
) {
    public static SpaceInfoResponseDto of(
            Long spaceId, String spaceName,String floor ,String address) {
        return new SpaceInfoResponseDto(spaceId, spaceName,floor, address);
    }
}
