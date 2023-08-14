package com.green.secondproject.sign;

import com.green.secondproject.config.RedisService;
import com.green.secondproject.config.security.AuthenticationFacade;
import com.green.secondproject.config.security.JwtTokenProvider;
import com.green.secondproject.config.security.PasswordEncoderConfiguration;
import com.green.secondproject.config.security.UserMapper;
import com.green.secondproject.sign.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({SignService.class, PasswordEncoderConfiguration.class, JwtTokenProvider.class})
@TestPropertySource(properties = "file.dir=/home/download")
@TestPropertySource(properties = "springboot.jwt.access-secret=DeaguGreenArtAcademyClass502ForJavaSpringBootaaaasdf")
@TestPropertySource(properties = "springboot.jwt.refresh-secret=HelloWorldRefreshTokenCreateBySecretHaHaaasdfasdf")
@Slf4j
class SignServiceTest {

    @MockBean
    private UserMapper mapper;
    @MockBean
    private AuthenticationFacade facade;
    @MockBean
    private RedisService redisService;

    @Autowired
    private SignService service;
    @Autowired
    private PasswordEncoder encoder;

    @Test
    void 학생_회원가입() {
        SignUpParam std = SignUpParam.builder()
                .pw("1111")
                .role("STD")
                .build();

        MultipartFile pic = new MockMultipartFile(
                "pic", "pic.jpg", "jpg", "pic".getBytes());

        when(mapper.save(any())).thenReturn(1);
        SignUpResultDto sResult1 = service.signUp(std, pic, null);
        assertTrue(sResult1.isSuccess());

        verify(mapper).save(any());
    }

    @Test
    void 선생님_회원가입() {
        SignUpParam tc = SignUpParam.builder()
                .pw("1111")
                .role("TC")
                .build();

        MultipartFile pic = new MockMultipartFile(
                "pic", "pic.jpg", "jpg", "pic".getBytes());
        MultipartFile aprPic = new MockMultipartFile(
                "aprPic", "aprPic.jpg", "jpg", "aprPic".getBytes());

        when(mapper.save(any())).thenReturn(1);
        SignUpResultDto tResult1 = service.signUp(tc, pic, aprPic);
        assertTrue(tResult1.isSuccess());

        verify(mapper).save(any());
    }

    @Test
    void signIn() {
        SignInParam p = new SignInParam();
        p.setEmail("test@gmail.com");
        p.setPw("1111");
        String ip = "127.0.0.1";

        UserVo user = new UserVo(1L, "test@gmail.com", encoder.encode("1111"), "테스트",
                1L, "함지고등학교", "1", "1", null, "STD", 1);

        when(mapper.selUserByEmail(any())).thenReturn(user);

        SignInResultDto result = service.signIn(p, ip);
        log.info("result: {}", result);

        assertNotNull(result.getRefreshToken());
        assertEquals(user.getRole(), result.getRole());

        verify(mapper).selUserByEmail(any());
    }

    @Test
    void refreshToken() {
    }

    @Test
    void logout() {
    }

    @Test
    void mailCheck() {
    }
}