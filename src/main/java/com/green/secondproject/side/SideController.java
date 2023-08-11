package com.green.secondproject.side;

import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.side.model.SideVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/side")
@Tag(name = "사이드 메뉴")
public class SideController {

    @Value("${file.imgPath}")
    private String imgPath;

    @GetMapping
    @Operation(summary = "사이드 메뉴 유저 정보 조회")
    public SideVo getUserInfo(@AuthenticationPrincipal MyUserDetails user) {
        return user == null ? null : SideVo.builder()
                .pic(String.format("%s/%d/%s", imgPath, user.getUserId(), user.getPic()))
                .nm(user.getNm())
                .email(user.getEmail())
                .build();
    }
}
