package popcong.app.domain.user.model;

import org.springframework.web.multipart.MultipartFile;

public record UploadItem(
        DocumentType type,
        MultipartFile file
) {}
