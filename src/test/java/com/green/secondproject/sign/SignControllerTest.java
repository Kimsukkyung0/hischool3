package com.green.secondproject.sign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.green.secondproject.sign.model.SignUpParam;
import com.green.secondproject.sign.model.SignUpResultDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignController.class)
class SignControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SignService service;

    @Test
    void signIn() {
    }

    @Test
    @DisplayName("선생님 회원가입")
    void signUp() throws Exception {
        SignUpResultDto resultDto = new SignUpResultDto();
        resultDto.setSuccess(true);
        resultDto.setCode(0);
        resultDto.setMsg("success");

        given(service.signUp(any(),any(),any())).willReturn(resultDto);

        String originalFileNm = "9084c915-39f8-410f-9934-22ac5b573426.png";
        String contentType = "png";
        String filePath = "D:/home/download/todo/18/" + originalFileNm;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        FileInputStream fileInputStream2 = new FileInputStream(filePath);

        MockMultipartFile pic = new MockMultipartFile("pic", originalFileNm, contentType, fileInputStream);
        MockMultipartFile aprPic = new MockMultipartFile("aprPic", originalFileNm, contentType, fileInputStream2);
        SignUpParam param = SignUpParam.builder()
                .nm("test")
                .email("test@gmail.com")
                .pw("1111")
                .phone("01000000000")
                .birth(LocalDate.now())
                .grade("1")
                .schoolNm("경원고등학교")
                .classNm("1")
                .role("TC")
                .address("대구시 중구")
                .build();

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String resJson = om.writeValueAsString(resultDto);
        String paramJson = om.writeValueAsString(param);

        MockMultipartFile userInfo = new MockMultipartFile("userInfo", "userInfo",
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
    void refreshToken() {
    }
}