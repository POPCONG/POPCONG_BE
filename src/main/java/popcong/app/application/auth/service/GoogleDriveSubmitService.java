package popcong.app.application.auth.service;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import popcong.app.application.auth.port.in.GoogleDriveSubmitUseCase;
import popcong.app.domain.user.model.SignUpUserType;
import popcong.app.infra.config.gcp.GcpApiProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class GoogleDriveSubmitService implements GoogleDriveSubmitUseCase {

    private Drive drive;
    private GcpApiProperties gcpApiProperties;

    @Override
    public String submitUserDocs(
            SignUpUserType userType, // GUEST, HOST
            Long userId,
            String email,
            String filename,
            InputStream content,
            String fileFormatType // nullable
    ) {
        try {
            // drive id, name 가져오기
            String rootDriveId = getDriveId(userType);

            // drive 이름 생성
            String userDriveName = userId + "_" + sanitize(email);

            String userDriveId = setUserDriveUnderRoot(rootDriveId, userDriveName);

            return uploadFile(userDriveId, filename, content, safeMime(fileFormatType));
        } catch (IOException e) {
            throw new RuntimeException("Google Drive 업로드 실패", e);
        }
    }


    // 유저 타입에 따라 다른 Google Drive ID 가져오기
    private String getDriveId(SignUpUserType userType) {
        return (Objects.equals(userType, SignUpUserType.GUEST))
                ? gcpApiProperties.getGuestDriveId() : gcpApiProperties.getHostDriveId();
    }

    private String setUserDriveUnderRoot(
            String rootDriveId,
            String folderName
    ) throws IOException {
        String q = "mimeType='application/vnd.google-apps.folder' " +
                "and name='" + escape(folderName) + "' " +
                "and '" + rootDriveId + "' in parents " +
                "and trashed=false";

        FileList fileList = drive.files().list()
                .setQ(q)
                .setSupportsAllDrives(true)
                .setIncludeItemsFromAllDrives(true)
                .setSpaces("drive")
                .setFields("files(id)")
                .execute();

        if (fileList.getFiles() != null && !fileList.getFiles().isEmpty()) {
            return fileList.getFiles().get(0).getId();
        }

        File meta = new File();
        meta.setName(folderName);
        meta.setMimeType("application/vnd.google-apps.folder");
        meta.setParents(List.of(rootDriveId));

        File createdFile = drive.files().create(meta)
                .setSupportsAllDrives(true)
                .setFields("id")
                .execute();

        return createdFile.getId();
    }

    // 파일 업로드
    private String uploadFile(
            String parentDriveId,
            String fileName,
            InputStream content,
            String fileFormatType
    ) throws IOException {
        File file = new File();
        file.setName(fileName);
        file.setParents(List.of(parentDriveId));

        var media = new InputStreamContent(fileFormatType, content);

        File created = drive.files().create(file, media)
                .setSupportsAllDrives(true)
                .setFields("id")
                .execute();

        return created.getId();
    }

    // ===== 파일명 형식 관련 valid 처리 =====
    private static String safeMime(String mime) {
        return (mime == null || mime.isBlank()) ? "application/octet-stream" : mime;
    }

    private static String sanitize(String s) {
        return s.replaceAll("[\\\\/:*?\"<>|#\\[\\]]", "_").trim();
    }

    private static String escape(String s) {
        return s.replace("'", "\\'");
    }
}
