package popcong.app.application.auth.port.in;

import popcong.app.domain.user.model.SignUpUserType;
import popcong.app.domain.user.model.UserRole;

import java.io.InputStream;

public interface GoogleDriveSubmitUseCase {
    String submitUserDocs(
            SignUpUserType userRole,
            Long userId,
            String email,
            String filename,
            InputStream content,
            String fileFormatType
    );
}
