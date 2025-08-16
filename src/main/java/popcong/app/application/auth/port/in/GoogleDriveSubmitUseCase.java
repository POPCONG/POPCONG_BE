package popcong.app.application.auth.port.in;

import popcong.app.domain.user.model.SignUpUserType;
import popcong.app.domain.user.model.UploadItem;

import java.util.List;

public interface GoogleDriveSubmitUseCase {
    List<String> submitUserDocs(
            SignUpUserType userRole,
            Long userId,
            String email,
            List<UploadItem> items
    );
}
