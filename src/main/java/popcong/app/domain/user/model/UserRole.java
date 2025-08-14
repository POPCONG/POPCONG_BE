package popcong.app.domain.user.model;

public enum UserRole {
    GUEST, // 회원 정보가 등록되지 않은 사용자
    GENERAL, // 일반 사용자 (임차인)
    PENDING, // 호스트 신청 서류 제출자
    HOST // 호스트 (임대인)
}
