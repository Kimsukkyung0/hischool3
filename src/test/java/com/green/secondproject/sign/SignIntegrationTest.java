package com.green.secondproject.sign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.green.secondproject.IntegrationTest;
import com.green.secondproject.config.RedisService;
import com.green.secondproject.config.security.UserMapper;
import com.green.secondproject.config.security.model.RedisJwtVo;
import com.green.secondproject.sign.model.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class SignIntegrationTest extends IntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private RedisService service;

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

        RedisJwtVo vo = om.readValue(data, RedisJwtVo.class);

        assertEquals(resultDto.getAccessToken(), vo.getAccessToken());
        assertEquals(resultDto.getRefreshToken(), vo.getRefreshToken());
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
    }
}
