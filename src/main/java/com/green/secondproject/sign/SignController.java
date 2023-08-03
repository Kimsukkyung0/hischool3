package com.green.secondproject.sign;

import com.green.secondproject.CommonRes;
import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.sign.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "로그인/회원가입")
public class SignController {
    private final SignService SERVICE;
    private final EmailService emailService;

    //ApiParam은 문서 자동화를 위한 Swagger에서 쓰이는 어노테이션이고
    //RequestParam은 http 로부터 요청 온 정보를 받아오기 위한 스프링 어노테이션이다.


    @PostMapping("/sign-in")
    @Operation(summary = "로그인", description = """
            "email": ex) "test@gmail.com"<br>
            "pw": ex) "1111"
            """)
    public SignInResultDto signIn(HttpServletRequest req, @RequestBody SignInParam p) throws RuntimeException {
        String ip = req.getRemoteAddr();
        log.info("[signIn] 로그인을 시도하고 있습니다. email: {}, pw: {}, ip: {}", p.getEmail(), p.getPw(), ip);

        SignInResultDto dto = SERVICE.signIn(p, ip);
        if(dto.getCode() == CommonRes.SUCCESS.getCode()) {
            log.info("[signIn] 정상적으로 로그인 되었습니다. email: {}, token: {}", p.getEmail(), dto.getAccessToken());
        }

        return dto;
    }

    @PostMapping(value = "/sign-up", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "회원가입", description = """
            "email": ex) "std1@gmail.com"<br>
            "pw": ex) "1111"<br>
            "nm": ex) "김학생"<br>
            "schoolNm": ex) "오성고등학교"<br>
            "grade": ex) "1"<br>
            "classNum": ex) "1"<br>
            "birth": ex) "2003-08-02"<br>
            "phone": ex) "010-2739-3928"<br>
            "address": ex) "대구시 북구"<br>
            "role": ex) 선생님이면 TC, 학생이면 STD<br><br>
            "pic": 프로필 사진<br>
            "aprPic": 선생님 인증사진
            """)
    public SignUpResultDto signUp(@RequestPart SignUpParam p, @RequestPart MultipartFile pic,
                                  @RequestPart(required = false) MultipartFile aprPic) {
        return SERVICE.signUp(p, pic, aprPic);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "accessToken 재발행")
    public ResponseEntity<SignUpResultDto> refreshToken(HttpServletRequest req, @RequestBody TokenDto token) {
        SignUpResultDto dto = SERVICE.refreshToken(req, token.getRefreshToken());
        return dto == null ? ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null) : ResponseEntity.ok(dto);
    }

    @PostMapping("/mail-confirm")
    @Operation(summary = "이메일 인증")
    public String mailConfirm(@RequestParam String email) throws Exception {
        String code = emailService.sendSimpleMessage(email);
        log.info("인증코드 : " + code);
        return code;
    }

    @PostMapping("/code-confirm")
    @Operation(summary = "인증코드 확인", description = """
            인증 성공(1), 실패(0)<br>
            인증 코드는 1분간 유효
            """)
    public int codeConfirm(@RequestParam String code) throws ChangeSetPersister.NotFoundException {
        return emailService.verifyEmail(code);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest req) {
        SERVICE.logout(req);
        ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
                .maxAge(0)
                .path("/")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }

//    @GetMapping("/test")
//    public MyUserDetails test(@AuthenticationPrincipal MyUserDetails userDetails) {
//        log.info("userDetails: {}", userDetails);
//        return userDetails;
//    }

//    @GetMapping("/test")
//    public MyUserDetails test() {
//        log.info("userDetails: {}", SERVICE.test());
//        return SERVICE.test();
//    }
}
