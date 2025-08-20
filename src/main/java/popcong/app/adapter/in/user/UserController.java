package popcong.app.adapter.in.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import popcong.app.application.auth.port.in.GoogleDriveSubmitUseCase;
import popcong.app.application.image.port.out.ProfileImageUploadPort;
import popcong.app.application.image.service.ProfileImageService;
import popcong.app.application.user.dto.response.MyProfileResponseDto;
import popcong.app.application.user.dto.response.UserResponseDto;
import popcong.app.application.user.port.in.GetMyProfileUseCase;
import popcong.app.application.user.port.in.UpdateMyProfileUseCase;
import popcong.app.application.user.port.in.UserInfoUseCase;
import popcong.app.domain.user.model.*;
import popcong.app.global.dto.ResponseDto;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.AuthErrorCode;
import popcong.app.global.exception.error.CommonErrorCode;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final GoogleDriveSubmitUseCase googleDriveSubmitUseCase;
    private final UserInfoUseCase userInfoUseCase;

    private final GetMyProfileUseCase getMyProfileUseCase;
    private final UpdateMyProfileUseCase updateMyProfileUseCase;
    private final ProfileImageService profileImageService;
    private final ProfileImageUploadPort profileImageUploadPort;


    /**
     * GUEST 파일 업로드 API
     */
    @PostMapping(value = "/my/guest/upload-files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto<?> uploadGuestFiles(
            @AuthenticationPrincipal User user,
            @RequestPart(value = "copyOfIdentification") MultipartFile copyOfIdentification,
            @RequestPart(value = "businessLicense", required = false) MultipartFile businessLicense
    ) {
        if (user == null) {
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
        }

        if (copyOfIdentification.isEmpty()) {
            throw new BusinessException(CommonErrorCode.NO_REQUIRED_FILES);
        }

        // 리스트에 업로드 파일 추가
        List<UploadItem> items = new ArrayList<>();
        items.add(new UploadItem(DocumentType.COPY_OF_IDENTIFICATION, copyOfIdentification));

        if (businessLicense != null && !businessLicense.isEmpty()) {
            items.add(new UploadItem(DocumentType.BUSINESS_LICENSE, businessLicense));
        }

        // 업로드
        List<String> fileIds = googleDriveSubmitUseCase.submitUserDocs(
                SignUpUserType.GUEST,
                user.userId(),
                user.email(),
                items
        );

        userInfoUseCase.updateUserRoleToGeneral(user.userId());

        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "게스트 파일 업로드 완료",
                null
        );
    }


    /**
     * HOST 파일 업로드 API
     */
    @PostMapping(value = "/my/host/upload-files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto<?> uploadHostFiles(
            @AuthenticationPrincipal User user,
            @RequestPart(value = "copyOfIdentification") MultipartFile copyOfIdentification,
            @RequestPart(value = "buildingRegister") MultipartFile buildingRegister,
            @RequestPart(value = "leaseAgreement") MultipartFile leaseAgreement,
            @RequestPart(value = "copyOfBankBook", required = false) MultipartFile copyOfBankBook
    ) {
        if (user == null) {
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
        }

        if (copyOfIdentification.isEmpty() || buildingRegister == null || leaseAgreement == null) {
            throw new BusinessException(CommonErrorCode.NO_REQUIRED_FILES);
        }

        // 리스트에 업로드 파일 추가
        List<UploadItem> items = new ArrayList<>();
        items.add(new UploadItem(DocumentType.COPY_OF_IDENTIFICATION, copyOfIdentification));
        items.add(new UploadItem(DocumentType.BUILDING_REGISTER, buildingRegister));
        items.add(new UploadItem(DocumentType.LEASE_AGREEMENT, leaseAgreement));


        if (copyOfBankBook != null && !copyOfBankBook.isEmpty()) {
            items.add(new UploadItem(DocumentType.COPY_OF_BANKBOOK, copyOfBankBook));
        }

        // 업로드
        List<String> fileIds = googleDriveSubmitUseCase.submitUserDocs(
                SignUpUserType.HOST,
                user.userId(),
                user.email(),
                items
        );

        userInfoUseCase.updateUserRoleToPending(user.userId());

        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "호스트 파일 업로드 완료",
                null
        );
    }


    @GetMapping("/me")
    public ResponseDto<UserResponseDto> getUserInfo(@AuthenticationPrincipal User user) {
        if (user == null) {
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
        }

        log.info("사용자 조회 성공 : userId = {}, providerId = {}, email = {}", user.userId(), user.providerId(), user.email());

        UserResponseDto result = UserResponseDto.from(user);

        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "사용자 조회 성공",
                result
        );
    }

    // 내 프로필 조회
    @GetMapping("/mypage/me")
    public ResponseDto<MyProfileResponseDto> getMyProfile(@AuthenticationPrincipal User user) {
        if (user == null) throw new BusinessException(AuthErrorCode.UNAUTHORIZED);

        MyProfileResponseDto dto = getMyProfileUseCase.get(user.userId());

        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "내 프로필 조회 성공",
                dto
        );
    }

    //프로필 편집
    @PatchMapping(value = "/my/update-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto<Void> updateMyProfile(
            @AuthenticationPrincipal User user,
            @RequestPart(required = false) String name,
            @RequestPart(required = false) String introduction,
            @RequestPart(required = false) MultipartFile profileImage
    ) {
        if (user == null) throw new BusinessException(AuthErrorCode.UNAUTHORIZED);

        String profileImageUrl = null;
        if (profileImage != null && !profileImage.isEmpty()) {
            // S3 업로드 + IMAGE 테이블 반영 + USER.profile_image_url 동기화
            profileImageUrl = profileImageUploadPort.uploadProfileImage(user.userId(), profileImage);

        }
        updateMyProfileUseCase.update(user.userId(), name, introduction, profileImageUrl);
        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "프로필 편집 성공",
                null);
    }
}
