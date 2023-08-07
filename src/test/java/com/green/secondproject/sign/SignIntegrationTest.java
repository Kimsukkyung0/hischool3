//package com.green.secondproject.sign;
//
//import com.green.secondproject.config.security.UserMapper;
//import com.green.secondproject.sign.model.SignUpResultDto;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.ResponseEntity;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class SignIntegrationTest {
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    public void signUp() throws Exception {
//        // given
//        UserRegisterDto userRegisterDto = UserRegisterDto.builder()
//                .nickName("닉네임3")
//                .email("asdf@naver.com")
//                .password("123456")
//                .address("대전광역시")
//                .phone("010-0000-0000")
//                .role(Role.GUEST)
//                .build();
//
//        String url = "http://localhost:" + this.port + "/api/sign-up";
//
//        // when
//        ResponseEntity<SignUpResultDto> responseEntity = this.restTemplate.postForEntity(url, userRegisterDto, User.class);
//
//        // then
//        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(responseEntity.getBody().getNickName()).isEqualTo(userRegisterDto.getNickName());
//
//        List<User> all = this.userRepository.findAll();
//        Assertions.assertThat(all.get(0).getNickName()).isEqualTo(userRegisterDto.getNickName());
//
//    }
//}
