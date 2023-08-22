package com.green.secondproject.sign;

import com.green.secondproject.IntegrationTest;
import com.green.secondproject.common.config.redis.RedisService;
import com.green.secondproject.common.config.exception.MyErrorResponse;
import com.green.secondproject.common.config.security.UserMapper;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.sign.model.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class SignIntegrationTest extends IntegrationTest {
    @LocalServerPort
    private int port;

//    @Autowired
//    private TestRestTemplate restTemplate;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private RedisService service;

    @BeforeEach
    void beforeEach() {
        UserDetails user = createUserDetails();

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(
                user, user.getPassword(), user.getAuthorities()));
    }

    private UserDetails createUserDetails() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_TC");

        return MyUserDetails.builder()
                .userId(3L)
                .roles(roles)
                .build();
    }

    @Test
    @DisplayName("회원가입")
    public void signUp() {
        SignUpParam p = SignUpParam.builder()
                .nm("김학생")
                .email("std@gmail.com")
                .pw("1111")
                .phone("010-0000-0000")
                .birth(LocalDate.now())
                .grade("1")
                .schoolNm("함지고등학교")
                .classNum("1")
                .role("STD")
                .address("대구광역시 중구 중앙대로 394")
                .detailAddress("제일빌딩 5F")
                .build();

        String originalFileNm = "test1.png";
        String contentType = "png";

        MockMultipartFile pic = new MockMultipartFile("pic", originalFileNm, contentType, "pic".getBytes());
        MockMultipartFile aprPic = new MockMultipartFile("aprPic", originalFileNm, contentType, "aprPic".getBytes());

        HttpHeaders headers = new HttpHeaders(); //헤더와 본문이 있는 HttpEntity를 만든다.
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>(); //LinkedMultiValueMap은 LinkedList의 각 키에 대해 여러 값을 저장하는 LinkedHashMap을 래핑
        body.add("p", p);
        body.add("pic", pic.getResource());
        body.add("aprPic", aprPic.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String serverUrl = "http://localhost:" + port + "/api/sign-up";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SignUpResultDto> response = restTemplate
                .postForEntity(serverUrl, requestEntity, SignUpResultDto.class);

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().isSuccess()).isEqualTo(true);

        UserVo user = mapper.selUserByEmail(p.getEmail());
        Assertions.assertThat(user.getNm()).isEqualTo(p.getNm());
    }

    @Test
    public void 로그인_성공() throws Exception {
        SignInParam p = new SignInParam();
        p.setEmail("tc@gmail.com");
        p.setPw("1111");

        String paramJson = om.writeValueAsString(p);

        MvcResult res = mvc.perform(post("/api/sign-in")
                .content(paramJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        log.info("res: {}", res.getResponse().getContentAsString());
        SignInResultDto resultDto = om.readValue(res.getResponse().getContentAsString(), SignInResultDto.class);

        String redisKey = "c:RT(Server):" + mapper.selUserByEmail(p.getEmail()).getUserId() + ":127.0.0.1";
        String data = service.getData(redisKey);
        log.info("data: {}", data);

        assertEquals(resultDto.getRefreshToken(), data);
    }

    @Test
    public void 존재하지_않는_이메일() throws Exception {
        SignInParam p = new SignInParam();
        p.setEmail("tc1@gmail.com");
        p.setPw("1111");

        String paramJson = om.writeValueAsString(p);

        MvcResult res = mvc.perform(post("/api/sign-in")
                        .content(paramJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andDo(print())
                .andReturn();

        log.info("res: {}", res.getResponse().getContentAsString());

        MyErrorResponse error = om.readValue(res.getResponse().getContentAsString(), MyErrorResponse.class);
        assertEquals("존재하지 않는 이메일", error.getMessage());
    }

    @Test
    public void 비밀번호_불일치() throws Exception {
        SignInParam p = new SignInParam();
        p.setEmail("tc@gmail.com");
        p.setPw("1112");

        String paramJson = om.writeValueAsString(p);

        MvcResult res = mvc.perform(post("/api/sign-in")
                        .content(paramJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andDo(print())
                .andReturn();

        log.info("res: {}", res.getResponse().getContentAsString());

        MyErrorResponse error = om.readValue(res.getResponse().getContentAsString(), MyErrorResponse.class);
        assertEquals("비밀번호 불일치", error.getMessage());
    }

    @Test
    public void 미승인_계정() throws Exception {
        SignInParam p = new SignInParam();
        p.setEmail("lbarbery0@soundcloud.com");
        p.setPw("1112");

        String paramJson = om.writeValueAsString(p);

        MvcResult res = mvc.perform(post("/api/sign-in")
                        .content(paramJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andDo(print())
                .andReturn();

        log.info("res: {}", res.getResponse().getContentAsString());

        MyErrorResponse error = om.readValue(res.getResponse().getContentAsString(), MyErrorResponse.class);
        assertEquals("비밀번호 불일치", error.getMessage());
    }

    @Test
    public void accessToken_재발행() throws Exception {
        String refreshToken = signIn();
        TokenDto dto = new TokenDto();
        dto.setRefreshToken(refreshToken);

        String dtoJson = om.writeValueAsString(dto);

        mvc.perform(post("/api/refresh-token")
                        .content(dtoJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void 이메일_인증코드_발송() throws Exception {
        String email = "gyujin0912@gmail.com";

        MvcResult res = mvc.perform(post("/api/mail-confirm")
                .param("email", email))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        log.info("code: {}", res.getResponse().getContentAsString());
    }

    @Test
    public void 인증코드_확인() throws Exception {
        String code = mailConfirm();

        MvcResult res = mvc.perform(post("/api/code-confirm")
                        .param("code", code))
                .andExpect(status().isOk())
                .andExpect((content().string("1")))
                .andDo(print())
                .andReturn();

        log.info("res: {}", res.getResponse().getContentAsString());
    }

//    @Test
//    public void 로그아웃() throws Exception {
//        MvcResult res = mvc.perform(post("/api/logout"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//
//        log.info("res: {}", res.getResponse().getContentAsString());
//    }

    public String signIn() throws Exception {
        SignInParam p = new SignInParam();
        p.setEmail("tc@gmail.com");
        p.setPw("1111");

        String paramJson = om.writeValueAsString(p);

        MvcResult res = mvc.perform(post("/api/sign-in")
                        .content(paramJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        log.info("res: {}", res.getResponse().getContentAsString());
        SignInResultDto resultDto = om.readValue(res.getResponse().getContentAsString(), SignInResultDto.class);

        String redisKey = "c:RT(Server):" + mapper.selUserByEmail(p.getEmail()).getUserId() + ":127.0.0.1";
        String data = service.getData(redisKey);
        log.info("data: {}", data);

        assertEquals(resultDto.getRefreshToken(), data);

        return resultDto.getRefreshToken();
    }
    public String mailConfirm() throws Exception {
        String email = "gyujin0912@gmail.com";

        MvcResult res = mvc.perform(post("/api/mail-confirm")
                        .param("email", email))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String code = res.getResponse().getContentAsString();
        log.info("code: {}", code);
        return code;
    }
}
