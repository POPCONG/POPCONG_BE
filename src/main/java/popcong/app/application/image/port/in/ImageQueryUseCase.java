package popcong.app.application.image.port.in;

import popcong.app.domain.image.model.ImageableType;

public interface ImageQueryUseCase {
    String getCoverImageUrl(ImageableType imageableType, Long imageableId);
}
