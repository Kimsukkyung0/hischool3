package com.green.secondproject.sign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.secondproject.CommonRes;
import com.green.secondproject.config.RedisService;
import com.green.secondproject.config.security.AuthenticationFacade;
import com.green.secondproject.config.security.JwtTokenProvider;
import com.green.secondproject.config.security.UserMapper;
import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.config.security.model.RedisJwtVo;
import com.green.secondproject.config.security.model.UserEntity;
import com.green.secondproject.config.security.model.UserTokenEntity;
import com.green.secondproject.sign.model.*;
import com.green.secondproject.utils.MyFileUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SignService {
    private final UserMapper MAPPER;
    private final JwtTokenProvider JWT_PROVIDER;
    private final PasswordEncoder PW_ENCODER;
    private final String FILE_DIR;
    private final AuthenticationFacade facade;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;

    @Autowired
    public SignService(UserMapper mapper, JwtTokenProvider provider, PasswordEncoder encoder,
                       @Value("${file.dir}") String fileDir, AuthenticationFacade facade, RedisService redisService,
                       ObjectMapper objectMapper) {
        MAPPER = mapper;
        JWT_PROVIDER = provider;
        PW_ENCODER = encoder;
        FILE_DIR = MyFileUtils.getAbsolutePath(fileDir);
        this.facade = facade;
        this.redisService = redisService;
        this.objectMapper = objectMapper;
    }

    public SignUpResultDto signUp(SignUpParam p, MultipartFile pic, MultipartFile aprPic) {
        log.info("[getSignUpResult] signDataHandler로 회원 정보 요청");

        File tempDic = new File(FILE_DIR, "/temp");
        if (!tempDic.exists()) {
            tempDic.mkdirs();
        }

        String savedPicNm = MyFileUtils.makeRandomFileNm(pic.getOriginalFilename());
        File tempPic = new File(tempDic.getPath(), savedPicNm);

        try {
            pic.transferTo(tempPic);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Long schoolId = MAPPER.selSchoolIdByNm(p.getSchoolNm());
        Long classId = MAPPER.selClassId(ClassDto.builder()
                .schoolId(schoolId)
                .year(String.valueOf(LocalDate.now().getYear()))
                .grade(p.getGrade())
                .classNum(p.getClassNum())
                .build());

        UserEntity entity = UserEntity.builder()
                .email(p.getEmail())
                .pw(PW_ENCODER.encode(p.getPw()))
                .nm(p.getNm())
                .pic(savedPicNm)
                .birth(p.getBirth())
                .phone(p.getPhone())
                .address(p.getAddress())
                .role(String.format("ROLE_%s", p.getRole().toUpperCase()))
                .classId(classId)
                .build();

        String savedAprPicNm = null;
        File tempAprPic = null;
        if (aprPic != null) {
            savedAprPicNm = MyFileUtils.makeRandomFileNm(aprPic.getOriginalFilename());
            tempAprPic = new File(tempDic.getPath(), savedAprPicNm);

            try {
                aprPic.transferTo(tempAprPic);
            } catch (IOException e) {
                e.printStackTrace();
            }
            entity.setAprPic(savedAprPicNm);
        }

        int result = MAPPER.save(entity);
        SignUpResultDto resultDto = new SignUpResultDto();

        if(result == 1) {
            log.info("[getSignUpResult] 정상 처리 완료");
            String targetDicPath = FILE_DIR + "/hiSchool/" + entity.getUserId();
            File targetDic = new File(targetDicPath);
            if (!targetDic.exists()) { targetDic.mkdirs(); }

            File targetPic = new File(targetDicPath, savedPicNm);
            tempPic.renameTo(targetPic);

            if (aprPic != null) {
                File targetAprPic = new File(targetDicPath, savedAprPicNm);
                tempAprPic.renameTo(targetAprPic);
            }

            setSuccessResult(resultDto);
        } else {
            log.info("[getSignUpResult] 실패 처리 완료");
            setFailResult(resultDto);
        }
        return resultDto;
    }

    public SignInResultDto signIn(SignInParam p, String ip) throws Exception {
        log.info("[getSignInResult] signDataHandler로 회원 정보 요청");
        UserVo user = MAPPER.selUserByEmail(p.getEmail());

        if (user == null) {
            throw new RuntimeException("존재하지 않는 이메일");
        }
        log.info("[getSignInResult] email: {}", p.getEmail());

        log.info("[getSignInResult] 패스워드 비교");
        if(!PW_ENCODER.matches(p.getPw(), user.getPw())) {
            throw new RuntimeException("비밀번호 불일치");
        }
        log.info("[getSignInResult] 패스워드 일치");

        String redisKey = String.format("c:RT(%s):%s:%s", "Server", user.getUserId(), ip);
        if (redisService.getData(redisKey) != null) {
            redisService.deleteData(redisKey);
        }

        log.info("[getSignInResult] access_token 객체 생성");
        String accessToken = JWT_PROVIDER.generateJwtToken(String.valueOf(user.getUserId()),
                Collections.singletonList(user.getRole()), JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);
        String refreshToken = JWT_PROVIDER.generateJwtToken(String.valueOf(user.getUserId()),
                Collections.singletonList(user.getRole()), JWT_PROVIDER.REFRESH_TOKEN_VALID_MS, JWT_PROVIDER.REFRESH_KEY);

        RedisJwtVo redisJwtVo = RedisJwtVo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        String value = objectMapper.writeValueAsString(redisJwtVo);
        redisService.setData(redisKey, value);

        log.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto dto = SignInResultDto.builder()
                                .accessToken(accessToken)
                                .refreshToken(refreshToken)
                                .role(user.getRole())
                                .build();

        log.info("[getSignInResult] SignInResultDto 객체 값 주입");
        setSuccessResult(dto);
        return dto;
    }

    public String refreshToken(HttpServletRequest req, String refreshToken) throws RuntimeException {
        String error = "유효하지 않은 토큰";
        if(!(JWT_PROVIDER.isValidateToken(refreshToken, JWT_PROVIDER.REFRESH_KEY))) { return error; }

        String ip = req.getRemoteAddr();
        String accessToken = JWT_PROVIDER.resolveToken(req, JWT_PROVIDER.TOKEN_TYPE);
        Claims claims = JWT_PROVIDER.getClaims(refreshToken, JWT_PROVIDER.REFRESH_KEY);
        if(claims == null) { return error; }

        String strIuser = claims.getSubject();
        Long iuser = Long.valueOf(strIuser);

        String redisKey = String.format("c:RT(%s):%s:%s", "Server", iuser, ip);
        String value = redisService.getData(redisKey);
        if (value == null) {
            return error;
        }

        try {
            RedisJwtVo redisJwtVo = objectMapper.readValue(value, RedisJwtVo.class);
            if (!redisJwtVo.getAccessToken().equals(accessToken) ||
                    !redisJwtVo.getRefreshToken().equals(refreshToken)) {
                return error;
            }

            List<String> roles = (List<String>)claims.get("roles");
            String reAccessToken = JWT_PROVIDER.generateJwtToken(strIuser, roles,
                    JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);

            RedisJwtVo updateRedisJwtVo = RedisJwtVo.builder()
                    .accessToken(reAccessToken)
                    .refreshToken(redisJwtVo.getRefreshToken())
                    .build();
            String updateValue = objectMapper.writeValueAsString(updateRedisJwtVo);
            redisService.setData(redisKey, updateValue);

            return reAccessToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return error;
    }

    public void logout(HttpServletRequest req) {
        String accessToken = JWT_PROVIDER.resolveToken(req, JWT_PROVIDER.TOKEN_TYPE);
        Long iuser = facade.getLoginUserPk();
        String ip = req.getRemoteAddr();

        String redisKey = String.format("c:RT(%s):%s:%s", "Server", iuser, ip);
        String refreshTokenInRedis = redisService.getData(redisKey);
        if (refreshTokenInRedis != null) {
            redisService.deleteData(redisKey);
        }

        long expiration = JWT_PROVIDER.getTokenExpirationTime(accessToken, JWT_PROVIDER.ACCESS_KEY) -
                LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        log.info("expiration: {}", expiration);
        log.info("localDateTime-getTime(): {}", LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        redisService.setDataExpire(accessToken, "logout", expiration);
    }

    private void setSuccessResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonRes.SUCCESS.getCode());
        result.setMsg(CommonRes.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonRes.FAIL.getCode());
        result.setMsg(CommonRes.FAIL.getMsg());
    }

//    public MyUserDetails test() {
//        return facade.getLoginUser();
//    }
}

