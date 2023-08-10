package com.green.secondproject.student;

import com.green.secondproject.mealmenutable.MealMenuTableService;
import com.green.secondproject.student.model.StudentMockSumResultVo;
import com.green.secondproject.student.model.StudentSummarySubjectDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({StudentMapper.class})
class StudentServiceTest {
@MockBean
StudentMapper mapper;

    @Test
    @Disabled
    @Description("")
    void selMockTestResultByDates() {
        //학생test
        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
        dto.setUserId(40L);
        dto.setYear("2023");

        List<StudentMockSumResultVo> list = new ArrayList<>();
        when(mapper.selMockTestResultByDates(dto)).thenReturn(list);

//        List<StudentMockSumResultVo> actualList = mapper.selMockTestResultByDates(dto);
//
//        StudentMockSumResultVo voForComp = new StudentMockSumResultVo();
//        assertEquals(actualList.get(0).getMon(),dto.getMon());
//        assertEquals(actualList.get(0).getYear(),dto.getYear());


        //선생님test
        StudentSummarySubjectDto dto2 = new StudentSummarySubjectDto();
        dto.setUserId(41L);//선생님ID
        dto.setYear("2023");

        List<StudentMockSumResultVo> list2 = new ArrayList<>();
        when(mapper.selMockTestResultByDates(dto2)).thenReturn(list);
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

    @Test
    void getMidFinalFormOfDate() {
    }
}