//package com.green.secondproject.timetable;
//
//import com.green.secondproject.CommonUserUtilsForTest;
//import com.green.secondproject.timetable.model.TimeTableContainerVo;
//import com.green.secondproject.timetable.model.TimeTableGetDto;
//import com.green.secondproject.timetable.model.TimeTableVo;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
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
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
////@AutoConfigureMockMvc // 아래 init 이랑 중복
////@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//
//
//@WebMvcTest(TimetableController.class)
////        excludeFilters = {
////                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
////)
//@WebAppConfiguration
//@Disabled
//@AutoConfigureMockMvc(addFilters = false)
//@Slf4j
//class TimetableControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private TimetableService service;
//
//
//    @BeforeEach
//    @DisplayName("create mockUserDetails for Test")
//    void beforeEach() {
//        CommonUserUtilsForTest testUser = new CommonUserUtilsForTest();
//        UserDetails user = testUser.getStudentUserDetails();
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities()));
//    }
//    @Test
//    @DisplayName("시간표테스트")
//    @Disabled
////    @IfProfileValue(name = "springs.profile.active", value = "local")
//    void getTimeTableByClassOfTheWeekWithAuth() throws Exception {
//        //given
//        List<TimeTableVo> list = new ArrayList<>();
//
//        TimeTableContainerVo subResult = new TimeTableContainerVo("오성고등학교","1","1","1",list);
//        TimeTableGetDto dto = new TimeTableGetDto();
//        dto.setClassNum("1");
//        dto.setGrade("1");
//        dto.setSchoolNm("오성고등학교");
//        given(service.getTimeTableByClassAndTheWeek(any())).willReturn(subResult);
//        //when
//        mvc.perform(get("/api/timetable"))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        TimeTableGetDto dto2 = new TimeTableGetDto();
//        verify(service).getTimeTableByClassAndTheWeek(dto);
//        SecurityContextHolder.clearContext();
//    }
//}
//
//
