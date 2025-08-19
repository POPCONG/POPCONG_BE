//package popcong.app.application.user.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class ProfileImageRecordService {
//
//    private final SaveImagePort saveImagePort;
//
//    @Override
//    public void recordProfileImage(Long userId, String imageUrl) {
//        saveImagePort.upsertProfileImage(userId, imageUrl, 1); // 프로필은 항상 1순위
//    }
//}
