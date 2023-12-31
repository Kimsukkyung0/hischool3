//package com.green.secondproject.mealmenutable;
//
//import com.green.secondproject.CommonUserUtilsForTest;
//import com.green.secondproject.mealmenutable.model.MealTableContainerVo;
//import com.green.secondproject.mealmenutable.model.MealTableVo;
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
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(MealMenuTableController.class)
//@WebAppConfiguration
//@Disabled
//@AutoConfigureMockMvc(addFilters = false)
//class MealMenuTableControllerTest {
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private MealMenuTableService service;
//
//    CommonUserUtilsForTest testUser = new CommonUserUtilsForTest();
//    @BeforeEach
//    @DisplayName("테스트용 유저디테일 생성")
//    void beforeEach() {
//        UserDetails user = testUser.getStudentUserDetails();
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities()));
//    }
//
//    @Test
//    @DisplayName("급식표테스트(월간)")
//    @Disabled
//    void getMealTableBySchoolOfTheMonth() throws Exception {
//        String requestSchoolNm = "오성고등학교";
//        List<MealTableVo> list = new ArrayList<>();
//        MealTableContainerVo vo = new MealTableContainerVo(requestSchoolNm,"202305",list);
//        given(service.getMealTableBySchoolOfTheMonth(any())).willReturn(vo);
//
//        mvc.perform(get("/api/meal"))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        verify(service).getMealTableBySchoolOfTheMonth(requestSchoolNm);
//        assertEquals(requestSchoolNm,vo.getSchoolNm());
//        SecurityContextHolder.clearContext();
//
//    }
//
//    @Test
//    @DisplayName("급식표테스트(주간)")
//    @Disabled
//    void getMealTableBySchoolOfTheWeek() throws Exception {
//        String requestSchoolNm = "오성고등학교";
//        List<MealTableVo> list = new ArrayList<>();
//        MealTableContainerVo vo = new MealTableContainerVo(requestSchoolNm,"202305",list);
//        given(service.getMealTableBySchoolOfTheWeek(any())).willReturn(vo);
//
//        mvc.perform(get("/api/meal"))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        verify(service).getMealTableBySchoolOfTheWeek(requestSchoolNm);
//        assertEquals(requestSchoolNm,vo.getSchoolNm());
//        SecurityContextHolder.clearContext();
//    }
//}