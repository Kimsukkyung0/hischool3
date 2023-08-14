package com.green.secondproject.myapage;

import com.green.secondproject.config.RedisService;
import com.green.secondproject.config.security.JwtTokenProvider;
import com.green.secondproject.config.security.SecurityConfiguration;
import com.green.secondproject.config.security.UserMapper;
import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.mypage.MyPageController;
import com.green.secondproject.mypage.MyPageMapper;
import com.green.secondproject.mypage.MyPageService;
import com.green.secondproject.mypage.model.SelUserMyPageVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;



@Import({SecurityConfiguration.class, JwtTokenProvider.class})
@WebMvcTest(controllers = MyPageController.class)
@WebAppConfiguration
@Disabled
@Slf4j
class MyPageControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private MyPageService service;

    @MockBean
    private UserMapper mapper;

    @MockBean
    private RedisService redisService;


    @BeforeEach
    void beforeEach() {
        UserDetails user = createUserDetails();
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }

    private UserDetails createUserDetails() throws UsernameNotFoundException {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_STD");
        UserDetails user = MyUserDetails.builder()
                .userId(40L)
                .email("aa@test.com")
                .pw("123")
                .schoolNm("오성고등학교")
                .grade("1")
                .classNum("1")
                .pic("test.jpg")
                .roles(roles)
                .build();
        return user;
    }

    @Test
    @Disabled
    void selectMyPage() throws Exception {
        SelUserMyPageVo MyPageVo = new SelUserMyPageVo();
        given(service.selUserMyPage(any())).willReturn(MyPageVo);

        mvc.perform(get("/api/mypage"))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verify(service).selUserMyPage(any());
        SecurityContextHolder.clearContext();
    }
}

