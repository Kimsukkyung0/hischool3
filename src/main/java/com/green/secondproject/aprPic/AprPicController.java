package com.green.secondproject.aprPic;

import com.green.secondproject.common.entity.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apr-pic")
public class AprPicController {
    private final AprPicService service;

    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "선생님 인증 사진 등록")
    public UserEntity updAprPic(Long userId, @RequestPart MultipartFile aprPic) {
        return service.updAprPic(userId, aprPic);
    }
}
