package com.green.secondproject.admin;

import com.green.secondproject.admin.model.MainNoticeListVo;
import com.green.secondproject.admin.model.StatusVo;
import com.green.secondproject.common.config.etc.CommonRes;
import com.green.secondproject.common.entity.SchoolAdminEntity;
import com.green.secondproject.sign.SignService;
import com.green.secondproject.sign.model.SignInParam;
import com.green.secondproject.sign.model.SignInResultDto;
import com.green.secondproject.sign.model.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
@Tag(name = "관리자")
public class AdminController {
    private final AdminService service;
    private final SignService signService;

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

    @PostMapping("/refresh-token")
    @Operation(summary = "accessToken 재발행")
    public String refreshToken(HttpServletRequest req, @RequestBody TokenDto token) {
        return signService.refreshToken(req, token.getRefreshToken());
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃")
    public ResponseEntity<?> logout(HttpServletRequest req) {
        signService.logout(req);
        ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
                .maxAge(0)
                .path("/")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }

    @GetMapping("/status")
    @Operation(summary = "교원/학생 현황")
    public StatusVo getStatus() {
        return service.getStatus();
    }

    @GetMapping("/main-notice")
    @Operation(summary = "메인페이지 공지사항")
    public MainNoticeListVo getMainNotice() {
        return service.getMainNotice();
    }
}

//야 남규ㅠ진 코드 잘 짜면 다냐/?
//다냐고
// 왕꿈틀이 내가 다 먹는다 ㅋㅋ
// 규진이 꿈에 왕꿈틀이나올예정 힝
// 야 니 우산 뭐냐 도데체
//내가 정리 해놓음