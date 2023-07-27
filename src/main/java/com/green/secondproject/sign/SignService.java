//package com.green.secondproject.sign;
//
//import com.green.secondproject.CommonRes;
//import com.green.secondproject.config.security.JwtTokenProvider;
//import com.green.secondproject.config.security.UserDetailsMapper;
//import com.green.secondproject.config.security.model.UserEntity;
//import com.green.secondproject.config.security.model.UserTokenEntity;
//import com.green.secondproject.sign.model.SignInResultDto;
//import com.green.secondproject.sign.model.SignUpResultDto;
//import io.jsonwebtoken.Claims;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.List;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class SignService {
//    private final UserDetailsMapper MAPPER;
//    private final JwtTokenProvider JWT_PROVIDER;
//    private final PasswordEncoder PW_ENCODER;
//
//    public SignUpResultDto signUp(String email, String pw, String nm, String role) {
//        log.info("[getSignUpResult] signDataHandler로 회원 정보 요청");
//        UserEntity user = UserEntity.builder()
//                .email(email)
//                .pw(PW_ENCODER.encode(pw))
//                .nm(nm)
//                .role(String.format("ROLE_%s", role))
//                .build();
//        int result = MAPPER.save(user);
//        SignUpResultDto dto = new SignUpResultDto();
//
//        if(result == 1) {
//            log.info("[getSignUpResult] 정상 처리 완료");
//            setSuccessResult(dto);
//        } else {
//            log.info("[getSignUpResult] 실패 처리 완료");
//            setFailResult(dto);
//        }
//        return dto;
//    }
//
//    public SignInResultDto signIn(String id, String password, String ip) throws RuntimeException {
//        log.info("[getSignInResult] signDataHandler로 회원 정보 요청");
//        UserEntity user = MAPPER.getByUid(id);
//
//        log.info("[getSignInResult] id: {}", id);
//
//        log.info("[getSignInResult] 패스워드 비교");
//        if(!PW_ENCODER.matches(password, user.getPw())) {
//            throw new RuntimeException("비밀번호 다름");
//        }
//        log.info("[getSignInResult] 패스워드 일치");
//
//
//        log.info("[getSignInResult] access_token 객체 생성");
//        String accessToken = JWT_PROVIDER.generateJwtToken(String.valueOf(user.getUserId()), Collections.singletonList(user.getRole()), JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);
//        String refreshToken = JWT_PROVIDER.generateJwtToken(String.valueOf(user.getUserId()), Collections.singletonList(user.getRole()), JWT_PROVIDER.REFRESH_TOKEN_VALID_MS, JWT_PROVIDER.REFRESH_KEY);
//        UserTokenEntity tokenEntity = UserTokenEntity.builder()
//                .iuser(user.getUserId())
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .ip(ip)
//                .build();
//
//        int result = MAPPER.updUserToken(tokenEntity);
//
//        log.info("[getSignInResult] SignInResultDto 객체 생성");
//        SignInResultDto dto = SignInResultDto.builder()
//                                .accessToken(accessToken)
//                                .refreshToken(refreshToken)
//                                .build();
//
//        log.info("[getSignInResult] SignInResultDto 객체 값 주입");
//        setSuccessResult(dto);
//        return dto;
//    }
//
//    public SignInResultDto refreshToken(HttpServletRequest req, String refreshToken) throws RuntimeException {
//        if(!(JWT_PROVIDER.isValidateToken(refreshToken, JWT_PROVIDER.REFRESH_KEY))) {
//            return null;
//        }
//
//        String ip = req.getRemoteAddr();
//        String accessToken = JWT_PROVIDER.resolveToken(req, JWT_PROVIDER.TOKEN_TYPE);
//        Claims claims = JWT_PROVIDER.getClaims(refreshToken, JWT_PROVIDER.REFRESH_KEY);
//        if(claims == null) {
//            return null;
//        }
//        String strIuser = claims.getSubject();
//        Long iuser = Long.valueOf(strIuser);
//        List<String> roles = (List<String>)claims.get("roles");
//
//        UserTokenEntity p = UserTokenEntity.builder()
//                .iuser(iuser)
//                .ip(ip)
//                .build();
//        UserTokenEntity selResult = MAPPER.selUserToken(p);
//        if(selResult == null || !(selResult.getAccessToken().equals(accessToken) && selResult.getRefreshToken().equals(refreshToken))) {
//            return null;
//        }
//
//        String reAccessToken = JWT_PROVIDER.generateJwtToken(strIuser, roles, JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);
//        UserTokenEntity tokenEntity = UserTokenEntity.builder()
//                .iuser(iuser)
//                .ip(ip)
//                .accessToken(reAccessToken)
//                .refreshToken(refreshToken)
//                .build();
//
//        int updResult = MAPPER.updUserToken(tokenEntity);
//
//        return SignInResultDto.builder()
//                .accessToken(reAccessToken)
//                .refreshToken(refreshToken)
//                .build();
//    }
//
//    private void setSuccessResult(SignUpResultDto result) {
//        result.setSuccess(true);
//        result.setCode(CommonRes.SUCCESS.getCode());
//        result.setMsg(CommonRes.SUCCESS.getMsg());
//    }
//
//    private void setFailResult(SignUpResultDto result) {
//        result.setSuccess(false);
//        result.setCode(CommonRes.FAIL.getCode());
//        result.setMsg(CommonRes.FAIL.getMsg());
//    }
//}
//
