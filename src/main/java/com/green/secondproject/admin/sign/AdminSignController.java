package com.green.secondproject.admin.sign;

import com.green.secondproject.admin.sign.model.AdminParam;
import com.green.secondproject.common.config.etc.CommonRes;
import com.green.secondproject.common.entity.SchoolAdminEntity;
import com.green.secondproject.sign.model.SignInParam;
import com.green.secondproject.sign.model.SignInResultDto;
import com.green.secondproject.sign.model.SignUpParam;
import com.green.secondproject.sign.model.SignUpResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
@Tag(name = "관리자 로그인")
public class AdminSignController {
    private final AdminSignService service;

    @PostMapping("/sign-in")
    @Operation(summary = "로그인", description = """
            "email": ex) "test@gmail.com"<br>
            "pw": ex) "1111"
            """)
    public SignInResultDto signIn(HttpServletRequest req, @RequestBody SignInParam p) {
        String ip = req.getRemoteAddr();
        log.info("[signIn] 로그인을 시도하고 있습니다. email: {}, pw: {}, ip: {}", p.getEmail(), p.getPw(), ip);

        SignInResultDto dto = service.signIn(p, ip);
        if(dto.getCode() == CommonRes.SUCCESS.getCode()) {
            log.info("[signIn] 정상적으로 로그인 되었습니다. email: {}, token: {}", p.getEmail(), dto.getAccessToken());
        }

        return dto;
    }

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = """
            스웨거에서 관리자를 임의로 생성하기 위한 메소드입니다.
            """)
    public SchoolAdminEntity signUp(@RequestBody AdminParam p) {
        return service.signUp(p);
    }
}
