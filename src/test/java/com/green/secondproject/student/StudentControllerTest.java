//package com.green.secondproject.student;
//
//import com.green.secondproject.CommonUserUtilsForTest;
//import com.green.secondproject.student.model.*;
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
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(StudentController.class)
//@WebAppConfiguration
//@Disabled
//@AutoConfigureMockMvc(addFilters = false)
//@Slf4j
//
//class StudentControllerTest {
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private StudentService service;
//
//    CommonUserUtilsForTest testUser = new CommonUserUtilsForTest();
//    Long userId = 40L;
//
//    @BeforeEach
//    @DisplayName("create 유저Context")
//    void beforeEach() {
//        UserDetails user = testUser.getStudentUserDetails();
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities()));
//    }
//
//    @Test
//    @DisplayName("모의고사성적출력-날짜별")
//    void selMockTestResultByDates() throws Exception {
//        //예상결과값
//        List<StudentMockSumResultVo> list = new ArrayList<>();
//        StudentSummarySubjectDto testDto1 = new StudentSummarySubjectDto();
//        testDto1.setUserId(userId);
//
//        //whenthen (유저값)
//        given(service.selMockTestResultByDates(testDto1)).willReturn(list);
//
//        mvc.perform(get("/api/student/mock-table"))
//                .andExpect(status().isOk())
//                .andDo(print());
//        verify(service).selMockTestResultByDates(testDto1);
//
//        //whenthen2유저값+연도값
//        testDto1.setYear("2023");
//        given(service.selMockTestResultByDates(testDto1)).willReturn(list);
//
//        //whenthen3 유저+연도+월
//        testDto1.setMon("6");
//        given(service.selMockTestResultByDates(testDto1)).willReturn(list);
//
//    }
//
//    @Test
//    @DisplayName("모의고사주요과목 최고성적TEST")
//    void getHighestRatingsOfMockTest() throws Exception{
//        List<StudentSummarySubjectVo> list = new ArrayList<>();
//        given(service.getHighestRatingsOfMockTest(userId)).willReturn(list);
//        mvc.perform(get("/api/student/mock-highrating"))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        verify(service).getHighestRatingsOfMockTest(userId);
//
//    }
//
//    @Test
//    @DisplayName("모의고사 최근성적출력")
//    void getLatestRatingsOfMockTest() throws Exception{
//        List<StudentSummarySubjectVo> list = new ArrayList<>();
//        String compDate = "202306";
//        StudentSumContainerVo result = StudentSumContainerVo.builder().date(compDate).list(list).build();
//
//        //넘길 값 생성
//        StudentSummarySubjectDto testDto = new StudentSummarySubjectDto();
//        testDto.setUserId(userId);
////        testDto.setYear("2023");
////        testDto.setMon("6");
//        given(service.getLatestRatingsOfMockTest(testDto)).willReturn(result);
//        mvc.perform(get("/api/student/mock-currentrating"))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        verify(service).getLatestRatingsOfMockTest(testDto);
//        assertEquals(list,result.getList());
//        assertEquals(compDate,result.getDate());
//        assertEquals(result.toString(),result.toString());
//    }
//
//    @Test
//    @DisplayName("모의고사그래프 TEST")
//    void getMockTestSumGraph() throws Exception{
//        List<StudentTestSumGraphVo> list = new ArrayList<>();
//        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
//        dto.setUserId(userId);
//
//        given(service.getMockTestGraph(dto)).willReturn(list);
//        mvc.perform(get("/api/student/mock-graph"))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        verify(service).getMockTestGraph(dto);
//    }
//
//    @Test
//    @DisplayName("기간,날자별 내신기록GET TEST")
//    void selAcaTestResultByDatesAndPeriod() throws Exception{
//        //예상결과값
//        List<StudentAcaResultVo> list = new ArrayList<>();
//        StudentAcaResultsParam param = new StudentAcaResultsParam();
//        param.setUserId(userId);
//
//        //whenthen (유저값)
//        given(service.selAcaTestResultByDatesAndPeriod(param)).willReturn(list);
//
//        mvc.perform(get("/api/student/aca-table"))
//                .andExpect(status().isOk())
//                .andDo(print());
//        verify(service).selAcaTestResultByDatesAndPeriod(param);
//
//        //whenthen2유저값+연도값
//        param.setYear("2023");
//        given(service.selAcaTestResultByDatesAndPeriod(param)).willReturn(list);
//
//        //whenthen3 유저+연도+학기
//        param.setSemester(1);
//        given(service.selAcaTestResultByDatesAndPeriod(param)).willReturn(list);
//
//
//        //whenthen4 유저+연도+학기+중간기말
//        param.setMidFinal(1);
//        given(service.selAcaTestResultByDatesAndPeriod(param)).willReturn(list);
//    }
//
//    @Test
//    @DisplayName("내신주요과목 최고성적TEST")
//    void getHighestRatingsOfAcaTest() throws Exception {
//        List<StudentSummarySubjectVo> list = new ArrayList<>();
//        String compDate = "202306";
//
//
//        //넘길 값 생성
//        StudentSummarySubjectDto testDto = new StudentSummarySubjectDto();
//        testDto.setUserId(userId);
////        testDto.setYear("2023");
////        testDto.setMon("6");
//        given(service.getHighestRatingsOfAcaTest(testDto)).willReturn(list);
//        mvc.perform(get("/api/student/aca-highest"))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        verify(service).getHighestRatingsOfAcaTest(testDto);
//
//    }
//
//    @Test
//    @DisplayName("내신주요과목 최근성적TEST")
//    void getLatestRatingsOfAcaTest() throws Exception {
//        List<StudentSummarySubjectVo> list = new ArrayList<>();
//        String compDate = "202306";
//        StudentSumContainerVo result = StudentSumContainerVo.builder().date(compDate).list(list).build();
//
//        given(service.getLatestRatingsOfAcaTest(userId)).willReturn(result);
//        mvc.perform(get("/api/student/aca-latest"))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        verify(service).getLatestRatingsOfAcaTest(userId);
//    }
//
//    @Test
//    @DisplayName("학생내신그래프TEST")
//    void getAcaTestGraph() throws Exception {
//        List<StudentTestSumGraphVo> list = new ArrayList<>();
//        StudentSummarySubjectDto testDto = new StudentSummarySubjectDto();
//        testDto.setUserId(userId);
//
//        given(service.getAcaTestGraph(testDto)).willReturn(list);
//        mvc.perform(get("/api/student/aca-graph"))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        verify(service).getAcaTestGraph(testDto);
//
//    }
//}