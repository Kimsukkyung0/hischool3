package com.green.secondproject.student;

import com.green.secondproject.mealmenutable.MealMenuTableService;
import com.green.secondproject.student.model.StudentMockSumResultVo;
import com.green.secondproject.student.model.StudentSummarySubjectDto;
import com.green.secondproject.student.model.StudentSummarySubjectVo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
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
    @Description("가짜비교값test")
    void FakeListTestForMockTestResultByDates() {
        //학생test1
        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
        dto.setUserId(603L);
        dto.setYear("2023");

        //test용 가짜 list생성
        List<StudentMockSumResultVo>fakeList = new ArrayList<>();
        fakeList.add(StudentMockSumResultVo.builder()
                .year("2023")
                .mon("8")
                .cateName("과학")
                .nm("4")
                .standardScore("42")
                .rating("8")
                .percent("42")
                .build());
        when(mapper.selMockTestResultByDates(dto)).thenReturn(fakeList);

        //실제 리스트 받아오는 작업(비교용)
        List<StudentMockSumResultVo> actualList = mapper.selMockTestResultByDates(dto);

        //비어있지 않은 값을 기대한다.
        assertFalse(actualList.isEmpty());
        //가짜리스트를 포함하고 있지 않을 것이다.
        assertFalse(actualList.contains(fakeList));
        //그러나, 요청한 연도의 값은 같다.
        assertEquals(Integer.parseInt(fakeList.get(0).getYear()),Integer.parseInt(actualList.get(0).getYear()));
        //"과학"이라는 카테고리 이름은 포함하고 있지않을것이다.
        assertFalse(actualList.contains(fakeList.get(0).getCateName()));

    }

    @Test
    @Disabled
    @Description("예상비교값test")
    void ExpectedListTestForMockTestResultByDates() {
        //예상값과 일치하는 리스트생성
        List<StudentMockSumResultVo> expectedList = new ArrayList<>();
        expectedList.add(StudentMockSumResultVo.builder()
                .year("2023")
                .mon("6")
                .cateName("국어")
                .nm("국어")
                .standardScore("70")
                .rating("6")
                .percent("67")
                .build());
        expectedList.add(StudentMockSumResultVo.builder()
                .year("2023")
                .mon("6")
                .cateName("수학")
                .nm("수학")
                .standardScore("80")
                .rating("6")
                .percent("67")
                .build());
        expectedList.add(StudentMockSumResultVo.builder()
                .year("2023")
                .mon("6")
                .cateName("영어")
                .nm("영어")
                .standardScore("80")
                .rating("6")
                .percent("67")
                .build());
        //예상키값
        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
        dto.setUserId(40L);
        dto.setYear("2023");

        //실제 리스트를 받아오는 작업
        when(mapper.selMockTestResultByDates(dto)).thenReturn(expectedList);
        List<StudentMockSumResultVo> actualList = mapper.selMockTestResultByDates(dto);

        //not null check
        assertFalse(actualList.isEmpty());
        //실제로 값이 일치하는지 비교(0번째 리스트)
        assertEquals(Integer.parseInt(expectedList.get(0).getYear()),Integer.parseInt(actualList.get(0).getYear()));
        assertEquals(Integer.parseInt(expectedList.get(0).getMon()),Integer.parseInt(actualList.get(0).getMon()));
        assertEquals(expectedList.get(0).getCateName(),actualList.get(0).getCateName());
        assertEquals(expectedList.get(0).toString(),actualList.get(0).toString());

        ////실제로 값이 일치하는지 비교(1번째 리스트) + 랜덤 필드값 검사
        assertEquals(expectedList.get(1).toString(),actualList.get(1).toString());
        assertEquals(expectedList.get(1).getPercent(),actualList.get(1).getPercent());

        ////실제로 값이 일치하는지 비교(2번째 리스트) + 랜덤 필드값 검사
        assertEquals(expectedList.get(2).toString(),actualList.get(2).toString());
        assertEquals(expectedList.get(2).getPercent(),actualList.get(2).getPercent());
        assertEquals(expectedList.get(2).getRating(),actualList.get(2).getRating());
    }

    @Test
    @Description("최고모의고사성적등급테스트")
    void getHighestRatingsOfMockTest() {
        //없는 유저 ID
        Long notExistUserId = 60423L;

        List<StudentSummarySubjectVo> tmpList = new ArrayList<>();
        when(mapper.getHighestRatingsOfMockTest(notExistUserId)).thenReturn(tmpList);

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