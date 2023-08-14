package com.green.secondproject.student;

import com.green.secondproject.student.model.StudentMockSumResultVo;
import com.green.secondproject.student.model.StudentSummarySubjectDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentMapperTest {
    @Autowired
    private StudentMapper mapper;

    @Test
    @DisplayName("학생메인 내신그래프 mapper TEST")
    void selMockTestResultByDates() {
//        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
//        dto.setUserId(1L);
//
//        List<StudentMockSumResultVo> expectedList = new ArrayList<>();
//        expectedList.add(StudentMockSumResultVo.builder().year("2023").mon("6").cateName("국어").nm().standardScore().rating().percent().build()));
//        expectedList.add(StudentMockSumResultVo.builder().year("2023").mon("6").cateName("국어").nm().standardScore().rating().percent().build()));
    }

    @Test
    void getHighestRatingsOfMockTest() {
    }

    @Test
    void getLatestRatingsOfMockTest() {
    }

    @Test
    void getMockTestGraph() {
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