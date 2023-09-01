package com.green.secondproject.sign;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.secondproject.common.config.etc.EnrollState;
import com.green.secondproject.common.config.redis.RedisService;
import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.JwtTokenProvider;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.entity.VanEntity;
import com.green.secondproject.common.utils.ApiUtils;
import com.green.secondproject.common.utils.ResultUtils;
import com.green.secondproject.common.repository.SchoolRepository;
import com.green.secondproject.sign.model.*;
import com.green.secondproject.common.utils.MyFileUtils;
import com.green.secondproject.common.repository.UserRepository;
import com.green.secondproject.common.repository.VanRepository;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignService {
    private final JwtTokenProvider JWT_PROVIDER;
    private final PasswordEncoder PW_ENCODER;
    private final AuthenticationFacade facade;
    private final RedisService redisService;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final VanRepository vanRepository;
    private final EmailService emailService;

    @Value("${file.dir}")
    private String FILE_DIR;

    @Value("${my-api.key}")
    private String myApiKey;

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

        SchoolEntity schoolEntity = schoolRepository.findByCode(p.getSchoolCode());
        if (schoolEntity == null) {
            schoolEntity = SchoolEntity.builder()
                    .code(p.getSchoolCode())
                    .logo(p.getSchoolNm() + ".png")
                    .nm(p.getSchoolNm())
                    .build();
            schoolRepository.save(schoolEntity);
        }

        String year = String.valueOf(LocalDate.now().getYear());
        VanEntity vanEntity = vanRepository.findBySchoolEntityAndYearAndGradeAndClassNum(schoolEntity, year,
                p.getGrade(), p.getClassNum());

        if (vanEntity == null) {
            vanEntity = VanEntity.builder()
                    .schoolEntity(schoolEntity)
                    .year(year)
                    .grade(p.getGrade())
                    .classNum(p.getClassNum())
                    .build();
            vanRepository.save(vanEntity);
        }

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
                .enrollState(EnrollState.ENROLL)
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
            e.printStackTrace();
            ResultUtils.setFailResult(resultDto);
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

        ResultUtils.setSuccessResult(resultDto);
        return resultDto;
    }

    public SignInResultDto signIn(SignInParam p, String ip) {
        log.info("[getSignInResult] signDataHandler로 회원 정보 요청");

        UserEntity user = userRepository.findByEmail(p.getEmail());

        if (user == null) {
            throw new RuntimeException("존재하지 않는 이메일");
        }
        if(!PW_ENCODER.matches(p.getPw(), user.getPw())) {
            throw new RuntimeException("비밀번호 불일치");
        }
        if (user.getAprYn() == 0) {
            throw new RuntimeException("미승인 계정");
        }
        if (!EnrollState.ENROLL.equals(user.getEnrollState())) {
            throw new RuntimeException("탈퇴한 회원");
        }

        String redisKey = String.format("c:RT(%s):%s:%s", "Server", user.getUserId(), ip);
        if (redisService.getData(redisKey) != null) {
            redisService.deleteData(redisKey);
        }

        log.info("[getSignInResult] access_token 객체 생성");
        String accessToken = JWT_PROVIDER.generateJwtToken(String.valueOf(user.getUserId()),
                Collections.singletonList(user.getRoleType().getCode()),
                JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);
        String refreshToken = JWT_PROVIDER.generateJwtToken(String.valueOf(user.getUserId()),
                Collections.singletonList(user.getRoleType().getCode()),
                JWT_PROVIDER.REFRESH_TOKEN_VALID_MS, JWT_PROVIDER.REFRESH_KEY);

        redisService.setData(redisKey, refreshToken);

        log.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto dto = SignInResultDto.builder()
                                .accessToken(accessToken)
                                .refreshToken(refreshToken)
                                .role(user.getRoleType().getCode())
                                .build();

        log.info("[getSignInResult] SignInResultDto 객체 값 주입");
        ResultUtils.setSuccessResult(dto);
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
        List<String> roles = (List<String>)claims.get("roles");

        String redisKey = "ROLE_ADMIN".equals(roles.get(0)) ?
                String.format("c:RT(%s):ADMIN:%s:%s", "Server", iuser, ip) :
                String.format("c:RT(%s):%s:%s", "Server", iuser, ip);

        String redisRt = redisService.getData(redisKey);
        if (redisRt == null) {
            return error;
        }

        try {
            if (!redisRt.equals(refreshToken)) {
                return error;
            }

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
        MyUserDetails userDetails = facade.getLoginUser();

        String redisKey = "ROLE_ADMIN".equals(userDetails.getRoles().get(0)) ?
                String.format("c:RT(%s):ADMIN:%s:%s", "Server", iuser, ip) :
                String.format("c:RT(%s):%s:%s", "Server", iuser, ip);

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
        UserEntity user = userRepository.findByEmail(email);
        return user == null ? 1 : 0;
    }

    public List<SchoolRes> getSchoolList() {
        String json = ApiUtils.createWebClient().get().uri(uriBuilder -> uriBuilder.path("/schoolInfo")
                        .queryParam("KEY", myApiKey)
                        .queryParam("Type", "json")
                        .queryParam("pIndex", 1)
                        .queryParam("pSize", 500)
                        .queryParam("ATPT_OFCDC_SC_CODE", "D10")
                        .queryParam("SCHUL_KND_SC_NM", "고등학교")
                        .build()
                ).retrieve().bodyToMono(String.class)
                .block();

        ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<SchoolRes> schoolInfoList = new ArrayList<>();
        try {
            JsonNode jsonNode = om.readTree(json);
            List<SchoolVo> schoolList = om.convertValue(jsonNode.at("/schoolInfo/1/row"), new TypeReference<>() {});
            for (SchoolVo vo : schoolList) {
                if ("일반고".equals(vo.getType()) || "자율고".equals(vo.getType())) {
                    schoolInfoList.add(SchoolRes.builder()
                            .nm(vo.getNm())
                            .schoolCode(vo.getCode())
                            .build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return schoolInfoList;

//        List<SchoolEntity> schoolList = schoolRepository.findAll();
//        return schoolList.stream().map(schoolEntity -> SchoolVo.builder()
//                .schoolId(schoolEntity.getSchoolId())
//                .nm(schoolEntity.getNm())
//                .schoolCode(schoolEntity.getCode())
//                .build()).toList();
    }

    public List<Integer> getClassList(SchoolParam p) {
        String json = ApiUtils.createWebClient().get().uri(uriBuilder -> uriBuilder.path("/classInfo")
                        .queryParam("KEY", myApiKey)
                        .queryParam("Type", "json")
                        .queryParam("pIndex", 1)
                        .queryParam("pSize", 500)
                        .queryParam("ATPT_OFCDC_SC_CODE", "D10")
                        .queryParam("SD_SCHUL_CODE", p.getSchoolCode())
                        .queryParam("AY", LocalDate.now().getYear())
                        .queryParam("GRADE", p.getGrade())
                        .build()
                ).retrieve().bodyToMono(String.class)
                .block();

        ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<String> classList = new ArrayList<>();
        try {
            JsonNode jsonNode = om.readTree(json);
            List<ClassVo> classVoList = om.convertValue(jsonNode.at("/classInfo/1/row"), new TypeReference<>() {});
            for (ClassVo vo : classVoList) {
                classList.add(vo.getClassNm());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Integer> list = new ArrayList<>(classList.stream()
                .map(Integer::parseInt)
                .toList());
        Collections.sort(list);

        return list;
    }

    public String findPw(String email) throws Exception {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("존재하지 않는 이메일");
        }

        String newPw = emailService.sendFindPw(email);
        user.setPw(PW_ENCODER.encode(newPw));
        userRepository.save(user);
        return newPw;
    }
}

