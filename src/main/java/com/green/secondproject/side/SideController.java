package com.green.secondproject.side;

import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.header.HeaderService;
import com.green.secondproject.side.model.SideVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/side")
@Tag(name = "사이드 메뉴")
@RequiredArgsConstructor
public class SideController {
    private final SideService service;

    @GetMapping
    @Operation(summary = "사이드 메뉴 유저 정보 조회")
    public SideVo getUserInfo(@AuthenticationPrincipal MyUserDetails user) {
        return service.getUserInfo(user);
    }
}
