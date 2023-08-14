
package com.green.secondproject.sign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.green.secondproject.config.security.MyUserDetailsServiceImpl;
import com.green.secondproject.sign.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignController.class)
@AutoConfigureMockMvc(addFilters = false)
class SignControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private SignService service;

    @MockBean
    private EmailService emailService;

    @Test
    @DisplayName("로그인")
    void signIn() throws Exception {
        SignInResultDto dto = SignInResultDto.builder()
                .refreshToken("rt")
                .accessToken("at")
                .build();

        SignInParam p = new SignInParam();
        p.setEmail("test@gmail.com");
        p.setPw("1111");

        String json = mapper.writeValueAsString(p);
        String resultJson = mapper.writeValueAsString(dto);

        given(service.signIn(any(), any())).willReturn(dto);

        mvc.perform(post("/api/sign-in")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(resultJson))
                .andDo(print());

        verify(service).signIn(any(), any());
    }

    @Test
    @DisplayName("회원가입")
    void signUp() throws Exception {
        SignUpResultDto resultDto = new SignUpResultDto();
        resultDto.setSuccess(true);
        resultDto.setCode(0);
        resultDto.setMsg("success");

        given(service.signUp(any(),any(),any())).willReturn(resultDto);

        String originalFileNm = "test1.png";
        String contentType = "png";

        MockMultipartFile pic = new MockMultipartFile("pic", originalFileNm, contentType, "pic".getBytes());
        MockMultipartFile aprPic = new MockMultipartFile("aprPic", originalFileNm, contentType, "aprPic".getBytes());
        SignUpParam param = SignUpParam.builder()
                .nm("test")
                .email("test@gmail.com")
                .pw("1111")
                .phone("01000000000")
                .birth(LocalDate.now())
                .grade("1")
                .schoolNm("경원고등학교")
                .classNum("1")
                .role("TC")
                .address("대구시 중구")
                .build();

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String resJson = om.writeValueAsString(resultDto);
        String paramJson = om.writeValueAsString(param);

        MockMultipartFile userInfo = new MockMultipartFile("p", "p",
                "application/json", paramJson.getBytes(StandardCharsets.UTF_8));

        mvc.perform(multipart("/api/sign-up")
                .file(pic)
                .file(aprPic)
                .file(userInfo)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(resJson))
        .andDo(print());

        verify(service).signUp(any(),any(),any());
    }

    @Test
    @DisplayName("토큰 재발행")
    void refreshToken() throws Exception {
        String token = "token";
        given(service.refreshToken(any(),any())).willReturn(token);

        TokenDto dto = new TokenDto();
        dto.setRefreshToken("rt");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dto);

        mvc.perform(post("/api/refresh-token")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(token))
                .andDo(print());

        verify(service).refreshToken(any(), any());
    }

    @Test
    @DisplayName("메일 인증")
    void mailConfirm() throws Exception {
        String code = "123456";
        given(emailService.sendSimpleMessage(any())).willReturn(code);

        mvc.perform(post("/api/mail-confirm")
                .param("email", "test@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(content().string(code))
                .andDo(print());

        verify(emailService).sendSimpleMessage(any());
    }

    @Test
    @DisplayName("인증코드 확인")
    void codeConfirm() throws Exception {
        int expect = 1;
        given(emailService.verifyEmail(any())).willReturn(expect);

        mvc.perform(post("/api/code-confirm")
                        .param("code", "123456"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(print());

        verify(emailService).verifyEmail(any());
    }

    @Test
    @DisplayName("로그아웃")
    void logout() {

    }
}
