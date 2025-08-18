package popcong.app.application.image.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageUploadPort {
    String uploadProfileImage(Long userId, MultipartFile file);
}
