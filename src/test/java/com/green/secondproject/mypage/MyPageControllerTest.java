//package com.green.secondproject.mypage;
//
//import com.green.secondproject.CommonUserUtilsForTest;
//import com.green.secondproject.common.config.redis.RedisService;
//import com.green.secondproject.common.config.security.UserMapper;
//import com.green.secondproject.mypage.model.SelUserMyPageDto;
//import com.green.secondproject.mypage.model.SelUserMyPageVo;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
////@WebMvcTest(MyPageController.class)
//@SpringBootTest
//@WebAppConfiguration
//@Disabled
//@AutoConfigureMockMvc(addFilters = false)
//@Slf4j
//class MyPageControllerTest {
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private MyPageService service;
//
//    @MockBean
//    private UserMapper mapper;
//
//    @MockBean
//    private RedisService redisService;
//
//
//    @BeforeEach
//    void beforeEach() {
//        CommonUserUtilsForTest testUser = new CommonUserUtilsForTest();
//        UserDetails user = testUser.getStudentUserDetails();
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities()));
//    }
//
////    private UserDetails createUserDetails() throws UsernameNotFoundException {
////        List<String> roles = new ArrayList<>();
////        roles.add("ROLE_STD");
////        UserDetails user = MyUserDetails.builder()
////                .userId(40L)
////                .email("aa@test.com")
////                .pw("123")
////                .schoolNm("오성고등학교")
////                .grade("1")
////                .classNum("1")
////                .pic("test.jpg")
////                .roles(roles)
////                .build();
////        return user;
////    }
//
//    @Test
//    @Disabled
//    void selectMyPage() throws Exception {
////        SelUserMyPageVo MyPageVo = new SelUserMyPageVo();
////        given(service.selUserMyPage(any())).willReturn(MyPageVo);
//
//        SelUserMyPageVo vo = new SelUserMyPageVo();
//        vo.setUserId(1L);
//        vo.setEmail("aa@test.com");
//        vo.setSchnm("오성고등학교");
//        vo.setGrade("1");
//        vo.setVan("1");
//        vo.setPic("test.jpg");
//        vo.setRole("STD");
//        given(service.selUserMyPage(any())).willReturn(vo);
//
//        mvc.perform(get("/api/mypage/user-mypage"))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        SelUserMyPageDto dto = new SelUserMyPageDto();
//        verify(service).selUserMyPage(any());
//        SecurityContextHolder.clearContext();
//    }
//
//}
//
