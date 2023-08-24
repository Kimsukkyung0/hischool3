package com.green.secondproject.admin.sign;

import com.green.secondproject.admin.sign.model.AdminParam;
import com.green.secondproject.common.config.redis.RedisService;
import com.green.secondproject.common.config.security.JwtTokenProvider;
import com.green.secondproject.common.entity.SchoolAdminEntity;
import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.repository.SchoolAdminRepository;
import com.green.secondproject.common.utils.ResultUtils;
import com.green.secondproject.sign.model.SignInParam;
import com.green.secondproject.sign.model.SignInResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AdminSignService {
    private final SchoolAdminRepository repository;
    private final PasswordEncoder PW_ENCODER;
    private final RedisService redisService;
    private final JwtTokenProvider JWT_PROVIDER;

    public SignInResultDto signIn(SignInParam p, String ip) {
        //todo 학교 관리자 회원가입 여부
        final String ADMIN = "ROLE_ADMIN";
        SchoolAdminEntity schoolAdmin = repository.findByEmail(p.getEmail());

        if (schoolAdmin == null) {
            throw new RuntimeException("존재하지 않는 이메일");
        }
        if(!PW_ENCODER.matches(p.getPw(), schoolAdmin.getPw())) {
            throw new RuntimeException("비밀번호 불일치");
        }

        String redisKey = String.format("c:RT(%s):ADMIN:%s:%s", "Server", schoolAdmin.getAdminId(), ip);
        if (redisService.getData(redisKey) != null) {
            redisService.deleteData(redisKey);
        }

        String accessToken = JWT_PROVIDER.generateJwtToken(String.valueOf(schoolAdmin.getAdminId()),
                Collections.singletonList(ADMIN), JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);
        String refreshToken = JWT_PROVIDER.generateJwtToken(String.valueOf(schoolAdmin.getAdminId()),
                Collections.singletonList(ADMIN), JWT_PROVIDER.REFRESH_TOKEN_VALID_MS, JWT_PROVIDER.REFRESH_KEY);

        redisService.setData(redisKey, refreshToken);

        SignInResultDto dto = SignInResultDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(ADMIN)
                .build();

        ResultUtils.setSuccessResult(dto);
        return dto;
    }

    public SchoolAdminEntity signUp(AdminParam p) {
        return repository.save(SchoolAdminEntity.builder()
                .email(p.getEmail())
                .pw(PW_ENCODER.encode(p.getPw()))
                .schoolEntity(SchoolEntity.builder()
                        .schoolId(p.getSchoolId())
                        .build())
                .build());
    }
}
