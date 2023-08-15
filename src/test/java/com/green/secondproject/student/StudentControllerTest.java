package com.green.secondproject.student;

import com.green.secondproject.CommonUserUtilsForTest;
import com.green.secondproject.student.model.StudentSumContainerVo;
import com.green.secondproject.student.model.StudentSummarySubjectDto;
import com.green.secondproject.student.model.StudentSummarySubjectVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;

@WebMvcTest(StudentController.class)
@WebAppConfiguration
@Disabled
@AutoConfigureMockMvc(addFilters = false)
@Slf4j

class StudentControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentService service;

    CommonUserUtilsForTest testUser = new CommonUserUtilsForTest();
    @BeforeEach
    @DisplayName("create 유저Context")
    void beforeEach() {
        UserDetails user = testUser.getStudentUserDetails();
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities()));
    }

    @Test
    @DisplayName("모의고사성적출력-날짜별")
    void selMockTestResultByDates() {

    }

    @Test
    void getHighestRatingsOfMockTest() {
    }

    @Test
    @DisplayName("모의고사 최근성적출력")
    void getLatestRatingsOfMockTest() {
        List<StudentSummarySubjectVo> list = new ArrayList<>();
        String compDate = "202306";
        StudentSumContainerVo result = StudentSumContainerVo.builder().date(compDate).list(list).build();

        //넘길 값 생성
        StudentSummarySubjectDto testDto = new StudentSummarySubjectDto();
        testDto.setUserId(1000L);
        given(service.getLatestRatingsOfMockTest(testDto)).willReturn(result);
        assertEquals(list,result.getList());
        assertEquals(compDate,result.getDate());
        assertEquals(result.toString(),result.toString());
    }

    @Test
    void getMockTestSumGraph() {
    }

    @Test
    void selAcaTestResultByDatesAndPeriod() {
    }

    @Test
    void getHighestRatingsOfAcaTest() {
    }

    @Test
    void getLatestRatingsOfAcaTest() {
    }

    @Test
    void getAcaTestGraph() {
    }
}