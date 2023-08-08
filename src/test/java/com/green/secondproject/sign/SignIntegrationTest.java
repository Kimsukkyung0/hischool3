//package com.green.secondproject.sign;
//
//import com.green.secondproject.config.security.UserMapper;
//import com.green.secondproject.sign.model.SignUpParam;
//import com.green.secondproject.sign.model.SignUpResultDto;
//import com.green.secondproject.sign.model.UserVo;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.*;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.test.context.support.WithAnonymousUser;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDate;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class SignIntegrationTest {
//    @LocalServerPort
//    private int port;
//
////    @Autowired
////    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private UserMapper mapper;
//
//    @Test
//    public void signUp() {
//        SignUpParam p = SignUpParam.builder()
//                .nm("test")
//                .email("test@naver.com")
//                .pw("1111")
//                .phone("010-0000-0000")
//                .birth(LocalDate.now())
//                .grade("1")
//                .schoolNm("함지고등학교")
//                .classNum("1")
//                .role("TC")
//                .address("대구시 중구")
//                .build();
//
//        String originalFileNm = "test1.png";
//        String contentType = "png";
//
//        MockMultipartFile pic = new MockMultipartFile("pic", originalFileNm, contentType, "pic".getBytes());
//        MockMultipartFile aprPic = new MockMultipartFile("aprPic", originalFileNm, contentType, "aprPic".getBytes());
//
//        HttpHeaders headers = new HttpHeaders(); //헤더와 본문이 있는 HttpEntity를 만든다.
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>(); //LinkedMultiValueMap은 LinkedList의 각 키에 대해 여러 값을 저장하는 LinkedHashMap을 래핑
//        body.add("p", p);
//        body.add("pic", pic.getResource());
//        body.add("aprPic", aprPic.getResource());
//
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
//        String serverUrl = "http://localhost:" + port + "/api/sign-up";
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<SignUpResultDto> response = restTemplate
//                .postForEntity(serverUrl, requestEntity, SignUpResultDto.class);
//
//        // then
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(response.getBody().isSuccess()).isEqualTo(true);
//
//        UserVo user = mapper.selUserByEmail(p.getEmail());
//        Assertions.assertThat(user.getNm()).isEqualTo(p.getNm());
//    }
//}
