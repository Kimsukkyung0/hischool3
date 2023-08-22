package com.green.secondproject.sign;

import com.green.secondproject.common.config.etc.CommonRes;
import com.green.secondproject.common.config.redis.RedisService;
import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.JwtTokenProvider;
import com.green.secondproject.common.config.security.UserMapper;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.entity.VanEntity;
import com.green.secondproject.school.SchoolRepository;
import com.green.secondproject.sign.model.*;
import com.green.secondproject.common.utils.MyFileUtils;
import com.green.secondproject.user.UserRepository;
import com.green.secondproject.van.VanRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignService {
    private final UserMapper MAPPER;
    private final JwtTokenProvider JWT_PROVIDER;
    private final PasswordEncoder PW_ENCODER;
    private final AuthenticationFacade facade;
    private final RedisService redisService;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final VanRepository vanRepository;

    @Value("${file.dir}")
    private String FILE_DIR;

    public SignUpResultDto signUp(SignUpParam p, MultipartFile pic, MultipartFile aprPic) {
        String fileDir = MyFileUtils.getAbsolutePath(FILE_DIR);
        log.info("[getSignUpResult] signDataHandler로 회원 정보 요청");

        File tempDic = new File(fileDir, "/temp");
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

        SchoolEntity schoolEntity = schoolRepository.getReferenceById(p.getSchoolId());
        VanEntity vanEntity = vanRepository.findVanEntityBySchoolEntityAndYearAndGradeAndClassNum(schoolEntity,
                String.valueOf(LocalDate.now().getYear()), p.getGrade(), p.getClassNum());

        UserEntity entity = UserEntity.builder()
                .email(p.getEmail())
                .pw(PW_ENCODER.encode(p.getPw()))
                .nm(p.getNm())
                .pic(savedPicNm)
                .birth(p.getBirth())
                .phone(p.getPhone())
                .address(p.getAddress())
                .detailAddr(p.getDetailAddress())
                .roleType("TC".equalsIgnoreCase(p.getRole()) ? RoleType.TC : RoleType.STD)
                .vanEntity(vanEntity)
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

        SignUpResultDto resultDto = new SignUpResultDto();
        UserEntity result;
        try {
            result = userRepository.save(entity);
        } catch (Exception e) {
            setFailResult(resultDto);
            return resultDto;
        }

        log.info("[getSignUpResult] 정상 처리 완료");
        String targetDicPath = fileDir + "/hiSchool/userPic/" + result.getUserId();
        File targetDic = new File(targetDicPath);
        if (!targetDic.exists()) { targetDic.mkdirs(); }

        File targetPic = new File(targetDicPath, savedPicNm);
        tempPic.renameTo(targetPic);

        if (aprPic != null) {
            String targetDicPathApr = fileDir + "/hiSchool/userApr/" + result.getUserId();

            File targetDicApr = new File(targetDicPathApr);
            if (!targetDicApr.exists()) { targetDicApr.mkdirs(); }

            File targetAprPic = new File(targetDicPathApr, savedAprPicNm);
            tempAprPic.renameTo(targetAprPic);
        }

        setSuccessResult(resultDto);
        return resultDto;
    }

    public SignInResultDto signIn(SignInParam p, String ip) {
        log.info("[getSignInResult] signDataHandler로 회원 정보 요청");

        UserVo user = MAPPER.selUserByEmail(p.getEmail());

        if (user == null) {
            throw new RuntimeException("존재하지 않는 이메일");
        }
        if(!PW_ENCODER.matches(p.getPw(), user.getPw())) {
            throw new RuntimeException("비밀번호 불일치");
        }
        if (user.getAprYn() == 0) {
            throw new RuntimeException("미승인 계정");
        }

        String redisKey = String.format("c:RT(%s):%s:%s", "Server", user.getUserId(), ip);
        if (redisService.getData(redisKey) != null) {
            redisService.deleteData(redisKey);
        }

        log.info("[getSignInResult] access_token 객체 생성");
        String accessToken = JWT_PROVIDER.generateJwtToken(String.valueOf(user.getUserId()),
                Collections.singletonList(user.getRole()), JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);
        String refreshToken = JWT_PROVIDER.generateJwtToken(String.valueOf(user.getUserId()),
                Collections.singletonList(user.getRole()), JWT_PROVIDER.REFRESH_TOKEN_VALID_MS, JWT_PROVIDER.REFRESH_KEY);

        redisService.setData(redisKey, refreshToken);

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

        Claims claims = null;
        try {
            claims = JWT_PROVIDER.getClaims(refreshToken, JWT_PROVIDER.REFRESH_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (claims == null) {
            return error;
        }

        String strIuser = claims.getSubject();
        Long iuser = Long.valueOf(strIuser);
        String ip = req.getRemoteAddr();

        String redisKey = String.format("c:RT(%s):%s:%s", "Server", iuser, ip);
        String redisRt = redisService.getData(redisKey);
        if (redisRt == null) {
            return error;
        }

        try {
            if (!redisRt.equals(refreshToken)) {
                return error;
            }

            List<String> roles = (List<String>)claims.get("roles");

            return JWT_PROVIDER.generateJwtToken(strIuser, roles,
                    JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);
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

    public int mailCheck(String email) {
        UserVo user = MAPPER.selUserByEmail(email);
        return user == null ? 1 : 0;
    }

    public List<SchoolVo> getSchoolList() {
        List<SchoolEntity> schoolList = schoolRepository.findAll();
        return schoolList.stream().map(schoolEntity -> SchoolVo.builder()
                .schoolId(schoolEntity.getSchoolId())
                .nm(schoolEntity.getNm())
                .build()).toList();
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
}

