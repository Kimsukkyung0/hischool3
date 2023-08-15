package com.green.secondproject.teacher;

import com.green.secondproject.CommonUserUtilsForTest;

import com.green.secondproject.student.StudentService;
import com.green.secondproject.student.model.StudentAcaResultVo;
import com.green.secondproject.student.model.StudentAcaResultsParam;
import com.green.secondproject.student.model.StudentMockSumResultVo;
import com.green.secondproject.student.model.StudentSummarySubjectDto;
import com.green.secondproject.teacher.model.TeacherGraphContainerVo;
import com.green.secondproject.teacher.model.TeacherGraphVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherController.class)
@WebAppConfiguration
@Disabled
@AutoConfigureMockMvc(addFilters = false)
@Slf4j
class TeacherControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TeacherService service;

    @MockBean
    private StudentService stService;

    CommonUserUtilsForTest testUser = new CommonUserUtilsForTest();
    Long userId = 80L;

    @BeforeEach
    @DisplayName("create 선생님유저Context")
    void beforeEach() {
        UserDetails user = testUser.getTeacherUserDetails();
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities()));
    }

    @Test
    @DisplayName("TEST 선생님 - 담당학급 최근성적내신 등급비율")
    void teacherAcaGraph() throws Exception{
        List<List<TeacherGraphVo>> list = new ArrayList<>();
        String stdDate = "202306";
        TeacherGraphContainerVo result = TeacherGraphContainerVo.builder().date(stdDate).list(list).build();

        given(service.teacherAcaGraph(userId)).willReturn(result);

        mvc.perform(get("/api/teacher/aca-graph"))
                .andExpect(status().isOk())
                .andDo(print());
        verify(service,atLeastOnce()).teacherAcaGraph(any());
    }

    @Test
    @DisplayName("TEST 선생님-학생별 내신성적관리 테이블")
    void selAcaTestResultByDatesAndPeriod() throws Exception{
       //param1 : 유저
        List<StudentAcaResultVo> list = new ArrayList<>();
        StudentAcaResultsParam testParam = new StudentAcaResultsParam();
        testParam.setUserId(userId);

        given(stService.selAcaTestResultByDatesAndPeriod(testParam)).willReturn(list);

        mvc.perform(get("/api/teacher/aca/"+userId))
                .andExpect(status().isOk())
                .andDo(print());

        //param2 : 유저+연도
        testParam.setYear("2023");
        given(stService.selAcaTestResultByDatesAndPeriod(testParam)).willReturn(list);

        mvc.perform(get("/api/teacher/aca/"+userId))
                .andExpect(status().isOk())
                .andDo(print());

        //param2 : 유저+연도+학기
        testParam.setSemester(1);
        given(stService.selAcaTestResultByDatesAndPeriod(testParam)).willReturn(list);

        mvc.perform(get("/api/teacher/aca/"+userId))
                .andExpect(status().isOk())
                .andDo(print());

        //param3 : 유저+연도+학기 + 기말/중간
        testParam.setMidFinal(1);
        given(stService.selAcaTestResultByDatesAndPeriod(testParam)).willReturn(list);

        mvc.perform(get("/api/teacher/aca/"+userId))
                .andExpect(status().isOk())
                .andDo(print());

        verify(stService,atLeastOnce()).selAcaTestResultByDatesAndPeriod(any());
    }

    @Test
    @DisplayName("TEST 선생님-학생별 모의고사성적관리 테이블")
    void selMockTestResultByDates() throws Exception{
        List<StudentMockSumResultVo> list = new ArrayList<>();
        StudentSummarySubjectDto testDto = new StudentSummarySubjectDto();
        testDto.setUserId(userId);

        given(stService.selMockTestResultByDates(testDto)).willReturn(list);

        mvc.perform(get("/api/teacher/mock/"+userId))
                .andExpect(status().isOk())
                .andDo(print());

        //param2 : 유저+연도
        testDto.setYear("2023");
        given(stService.selMockTestResultByDates(testDto)).willReturn(list);

        mvc.perform(get("/api/teacher/mock/"+userId))
                .andExpect(status().isOk())
                .andDo(print());

        //param2 : 유저+연도+학기
        testDto.setMon("6");
        given(stService.selMockTestResultByDates(testDto)).willReturn(list);

        mvc.perform(get("/api/teacher/mock/"+userId))
                .andExpect(status().isOk())
                .andDo(print());

        verify(stService,atLeastOnce()).selMockTestResultByDates(any());


    }
}