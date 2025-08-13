package popcong.app.domain.image.model;

import java.time.LocalDateTime;

public record Image(
        Long imageId,
        String imageUrl,
        Integer saveOrder,//저장순서
        Long imageableId, //이미지가 연결된 대상의 ID
        ImageableType imageableType, //이미지가 연결된 테이블 타입
        LocalDateTime createdAt //저장일시
) {
}
