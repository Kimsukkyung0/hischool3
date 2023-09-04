package com.green.secondproject.sign;

import com.green.secondproject.common.config.etc.CommonRes;
import com.green.secondproject.sign.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public SignInResultDto signIn(HttpServletRequest req, @RequestBody SignInParam p) {
        String ip = req.getRemoteAddr();
        log.info("[signIn] 로그인을 시도하고 있습니다. email: {}, pw: {}, ip: {}", p.getEmail(), p.getPw(), ip);

        SignInResultDto dto = SERVICE.signIn(p, ip);
        if(dto.getCode() == CommonRes.SUCCESS.getCode()) {
            log.info("[signIn] 정상적으로 로그인 되었습니다. email: {}, token: {}", p.getEmail(), dto.getAccessToken());
        }

        return dto;
    }

    @Operation(summary = "회원가입", description = """
            "email": 이메일<br>
            "pw": 비밀번호<br>
            "nm": 이름<br>
            "schoolCode": 사용자가 선택한 학교의 학교코드<br>
            "schoolNm": 학교명<br>
            "grade": 학년<br>
            "classNum": 반<br>
            "birth": 생일<br>
            "phone": 연락처<br>
            "address": 주소<br>
            "detailAddress": 상세주소<br>
            "role": 권한 ex) 선생님(TC), 학생(STD)<br><br>
            "pic": 프로필 사진<br>
            "aprPic": 선생님 인증사진
            """)
    @PostMapping(value = "/sign-up", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SignUpResultDto signUp(@RequestPart SignUpParam p, @RequestPart MultipartFile pic,
                                  @RequestPart(required = false) MultipartFile aprPic) {
        return SERVICE.signUp(p, pic, aprPic);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "accessToken 재발행")
    public String refreshToken(HttpServletRequest req, @RequestBody TokenDto token) {
        return SERVICE.refreshToken(req, token.getRefreshToken());
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
    public int codeConfirm(@RequestParam String code) {
        return emailService.verifyEmail(code);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃")
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

    @GetMapping("/mail-check")
    @Operation(summary = "이메일 중복 확인", description = "사용 가능(1), 불가능(0)")
    public int mailCheck(@RequestParam String email) {
        return SERVICE.mailCheck(email);
    }

    @GetMapping("/school-list")
    @Operation(summary = "대구 일반, 자율고 리스트")
    public List<SchoolRes> getSchoolList() {
        return SERVICE.getSchoolList();
    }

    @GetMapping("/class-list")
    @Operation(summary = "선택한 학교, 학년의 반 리스트", description = """
            "schoolCode": ex) "7240223"<br>
            "grade": ex) "1"<br>
            """)
    public List<Integer> getClassList(SchoolParam p) {
        return SERVICE.getClassList(p);
    }

    @GetMapping("/pw-find")
    @Operation(summary = "비밀번호 찾기")
    public String findPw(String email) throws Exception {
        return SERVICE.findPw(email);
    }
}
