//package com.green.secondproject.subject;
//
//import com.green.secondproject.CommonUserUtilsForTest;
//import com.green.secondproject.common.config.redis.RedisService;
//import com.green.secondproject.teacher.subject.SubjectController;
//import com.green.secondproject.teacher.subject.SubjectService;
//import com.green.secondproject.teacher.subject.model.SubjectVo;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = SubjectController.class)
//@WebAppConfiguration
//@Disabled
//@AutoConfigureMockMvc(addFilters = false)
//@Slf4j
//public class SubjectControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private SubjectService serivce;
//
//    @MockBean
//    private RedisService redisService;
//    CommonUserUtilsForTest testUser = new CommonUserUtilsForTest();
//
//    @BeforeEach
//    @DisplayName("테스트용 유저디테일 생성")
//    void beforeEach() {
//        UserDetails user = testUser.getStudentUserDetails();
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities()));
//    }
//
//
//    //    private UserDetails createUserDetails() {
////        List<String> roles = new ArrayList<>();
////        roles.add("ROLE_TC");
////        //roles.add("ROLE_ADMIN");
////
////        UserDetails userDetails = MyUserDetails.builder()
////                .userId(3L)
////                .roles(roles)
////                .build();
////        return userDetails;
////    }
//    @Test
//    @DisplayName("subcate - 선생님과목계열리스트")
//    void subcate() throws Exception {
//        List<SubjectVo> list = new ArrayList<>();
//        String nm = "국어";
//        SubjectVo vo = new SubjectVo();
//        vo.setNm("국어");
//        vo.setCategoryId(1L);
//
//        list.add(vo);
//
//        given(serivce.subcate()).willReturn(list);
//        mvc.perform(get("/api/teacher/subject/category"))
//                .andExpect(status().isOk())
//                .andDo(print());
//        vo.setNm("국어");
//
//        given(serivce.subcate()).willReturn(list);
//        assertEquals(nm,vo.getNm());
//    }
//}
