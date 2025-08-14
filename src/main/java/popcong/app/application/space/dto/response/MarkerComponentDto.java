package popcong.app.application.space.dto.response;

public record MarkerComponentDto(
        Long spaceId,
        Double latitude, // 위도
        Double longitude, // 경도
        String spaceName, // 공간 이름
        String coverImageUrl, // 대표이미지 경로
        Integer rentalFee, // 대여료
        String address, // 주소
        Integer floor, // 층수
        Double rating, // 공간 평점
        Integer reviewCount, // 공간 후기 수
        Double distance // 현위치와의 거리
) {
}
