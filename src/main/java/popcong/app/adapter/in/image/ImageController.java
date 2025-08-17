package popcong.app.adapter.in.image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import popcong.app.adapter.out.persistence.image.entity.ImageJpaEntity;
import popcong.app.application.image.service.ImageService;
import popcong.app.domain.image.model.ImageableType;
import popcong.app.global.dto.ResponseDto;

import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {
    private final ImageService imageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto<ImageJpaEntity> upload(
            @RequestParam("imageableId") Long imageableId,
            @RequestParam("imageableType") ImageableType imageableType,
            @RequestParam("saveOrder") Integer saveOrder,
            @RequestPart("file") MultipartFile file
    ) {
        ImageJpaEntity saved = imageService.upload(imageableType,imageableId,saveOrder, file);
        return new ResponseDto<>(HttpStatus.CREATED.value(), "이미지 업로드 성공", saved);
    }

    @GetMapping
    public ResponseDto<List<ImageJpaEntity>> list(
            @RequestParam("imageableId") Long imageableId,
            @RequestParam("imageableType") ImageableType imageableType
    ) {
        List<ImageJpaEntity> images = imageService.list(imageableId, imageableType);
        return new ResponseDto<>(HttpStatus.OK.value(), "이미지 목록 조회 성공", images);
    }

}