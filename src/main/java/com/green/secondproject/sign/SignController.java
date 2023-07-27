//package com.green.secondproject.sign;
//
//import com.green.secondproject.CommonRes;
//import com.green.secondproject.sign.model.SignInResultDto;
//import com.green.secondproject.sign.model.SignUpResultDto;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/sign-api")
//public class SignController {
//    private final SignService SERVICE;
//
//    //ApiParam은 문서 자동화를 위한 Swagger에서 쓰이는 어노테이션이고
//    //RequestParam은 http 로부터 요청 온 정보를 받아오기 위한 스프링 어노테이션이다.
//
//
//    @PostMapping("/sign-in")
//    public SignInResultDto signIn(HttpServletRequest req, @RequestParam String id, @RequestParam String password) throws RuntimeException {
//
//        String ip = req.getRemoteAddr();
//        log.info("[signIn] 로그인을 시도하고 있습니다. id: {}, pw: {}, ip: {}", id, password, ip);
//
//        SignInResultDto dto = SERVICE.signIn(id, password, ip);
//        if(dto.getCode() == CommonRes.SUCCESS.getCode()) {
//            log.info("[signIn] 정상적으로 로그인 되었습니다. id: {}, token: {}", id, dto.getAccessToken());
//        }
//        return dto;
//    }
//
//    @PostMapping("/sign-up")
//    public SignUpResultDto signUp(@RequestParam String id
//                                , @RequestParam String pw
//                                , @RequestParam String nm
//                                , @RequestParam String role) {
//        log.info("[signUp] 회원가입을 수행합니다. id: {}, pw: {}, nm: {}, role: {}", id, pw, nm, role);
//        SignUpResultDto dto = SERVICE.signUp(id, pw, nm, role);
//        log.info("[signUp] 회원가입 완료 id: {}", id);
//        return dto;
//    }
//
//    @GetMapping("/refresh-token")
//    public ResponseEntity<SignUpResultDto> refreshToken(HttpServletRequest req, @RequestParam String refreshToken) {
//        SignUpResultDto dto = SERVICE.refreshToken(req, refreshToken);
//        return dto == null ? ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null) : ResponseEntity.ok(dto);
//    }
//}
