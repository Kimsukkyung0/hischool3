package com.green.secondproject.side;

import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.side.model.SideVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/side")
@Tag(name = "사이드 메뉴")
public class SideController {
    @GetMapping
    public SideVo getUserInfo(@AuthenticationPrincipal MyUserDetails user) {
        return user == null ? null : SideVo.builder()
                .pic(user.getPic())
                .nm(user.getNm())
                .email(user.getEmail())
                .build();
    }
}
