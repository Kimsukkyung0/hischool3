//package com.green.secondproject.timetable;
//
//import com.green.secondproject.config.security.model.MyUserDetails;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
//import org.springframework.test.annotation.IfProfileValue;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.*;
//
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.isNull;
//import static org.mockito.Mockito.verify;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@WebMvcTest(TimetableController.class)
//@AutoConfigureMockMvc
//@WebAppConfiguration
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class TimetableControllerTest{
//
//    @Autowired
//    private MockMvc mvc;
//    protected WebApplicationContext context;
//
//
//    @MockBean
//    private TimetableService service;
//    private TestUserDetailsService testUser;
//
//    @BeforeAll
//    @DisplayName("init-mvc")
//    void init() throws Exception {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .alwaysDo(print()).build();
//        //new PreAuthenticatedAuthenticationToken
//    }
//
//    @Test
//    @DisplayName("시간표테스트-권한없음")
//    @IfProfileValue(name = "spring.profile.active", value="local")
//    void getTimeTableByClassOfTheWeekWithoutAuth() throws Exception {
//        //권한 없는 유저
//        //emptyContext : when
//
////        ResultActions actions = mvc.perform(get("/api/timetable").with()
////                .andExpectAll(isNull(), status()
////                        .is4xxClientError(), status()
////                        .isUnauthorized())
////                .andDo(print());
//
//
//        verify(service).getTimeTableByClassAndTheWeek(myUserDetails);
//
//        SecurityContextHolder.clearContext();
//
//    }
//
//
//
//    @Test
//    @DisplayName("시간표테스트-학생권한")
//    void getTimeTableByClassOfTheWeekWithAuth() throws Exception {
//        service.getTimeTableByClassAndTheWeek(testUser.getTestUser());
//
//        SecurityContextHolder.getContext().setAuthentication(new PreAuthenticatedAuthenticationToken
//                (null, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_STD"))));

//        //null이 아니라면, 검사한다.
//        if(vo != null){
//
//            //예상한 값이 들어있는 가짜리스트 만들기
//            //5.22 1교시 국어/2교시 영어
//        List<TimeTableVo> mockTableVolist = new ArrayList<TimeTableVo>();
//        TimeTableVo mockSubVo = new TimeTableVo();//서브리스트
//        mockSubVo.setPeriod("1");//1교시
//        mockSubVo.setSubject("국어");//과목명
//        mockTableVolist.add(mockSubVo);
//
//        TimeTableVo mockSubVo2 = new TimeTableVo();
//        mockSubVo2.setPeriod("2");
//        mockSubVo2.setSubject("영어");
//        mockTableVolist.add(mockSubVo2);
//
//        TimeTableContainerVo mockVo = new TimeTableContainerVo("오성고등학교","1","1","1",mockTableVolist);
//
//        //실제넘어온 값(vo)와 가짜리스트(containerVo)와 비교 (학교명, 학년일치)
//        assertEquals(vo.getSchoolNm(),mockVo.getSchoolNm());
//        assertEquals(vo.getClassNm(),mockVo.getClassNm());
//
//        //실제넘어온 값(vo.list)와 가짜리스트(mocksubVolist)와 비교 (1교시,수업)
//        List<TimeTableVo> subList = vo.getList();
//
//        assertEquals(subList.get(0).getPeriod(),mockSubVo.getPeriod());
//        assertEquals(subList.get(0).getSubject(),mockSubVo.getSubject());
//
//        assertEquals(subList.get(1).getPeriod(),mockSubVo.getPeriod());
//        assertEquals(subList.get(1).getSubject(),mockSubVo.getSubject());
//        }
//        else{
//            assertNull(vo);
//        }

        //실제리스트와 전체비교가 힘들다
        // result action /status ok /넘어오는 데이터 타입만 확인


//        ResultActions ra = mvc.perform(get("/api/timetable")
//                                .contentType(MediaType.APPLICATION_JSON_UTF8));
//
////        ra.andExpect(status().isOk())//200을 기대하면..안되나
////                            .andDo(print());

//
//    }
//}