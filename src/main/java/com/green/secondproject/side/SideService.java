package com.green.secondproject.side;

import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.header.HeaderService;
import com.green.secondproject.header.model.SelSchoolLogoVo;
import com.green.secondproject.side.model.SideVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SideService {
    private final HeaderService service;

    @Value("${file.imgPath}")
    private String imgPath;
    public SideVo getUserInfo(@AuthenticationPrincipal MyUserDetails user) {
        if (user == null) {
            return null;
        }
        if ("ROLE_ADMIN".equals(user.getRoles().get(0))) {
            SelSchoolLogoVo logoVo = service.selSchoolLogo(user);
            return SideVo.builder()
                    .pic(logoVo.getLogo())
                    .nm("관리자")
                    .email(user.getEmail())
                    .build();
        }
        return SideVo.builder()
                .pic(String.format("%s/%d/%s", imgPath, user.getUserId(), user.getPic()))
                .nm(user.getNm())
                .email(user.getEmail())
                .build();
    }
}
