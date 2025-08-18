package popcong.app.application.image.port.out;

import popcong.app.domain.image.model.Image;
import popcong.app.domain.image.model.ImageableType;

import java.util.List;

public interface ImageQueryPort {

    List<Image> findSpaceImages(Long spaceId, ImageableType imageableType, int limit);
}
