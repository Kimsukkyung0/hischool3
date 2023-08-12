package com.green.secondproject.student;

import com.green.secondproject.mealmenutable.MealMenuTableService;
import com.green.secondproject.student.model.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import({StudentMapper.class})
class StudentServiceTest {
@MockBean
StudentMapper mapper;
@MockBean
StudentService service;

    @Test
    @Disabled
    @DisplayName("연월에따른 모의고사점수조회(+가짜유저)")
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


        verify(mapper).selMockTestResultByDates(dto);

    }

    @Test
    @Disabled
    @DisplayName("연월에따른 모의고사점수조회(+실유저)")
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

        ////값 비교(1번째 리스트) + 랜덤 필드값 검사
        assertEquals(expectedList.get(1).toString(),actualList.get(1).toString());
        assertEquals(expectedList.get(1).getPercent(),actualList.get(1).getPercent());

        ////값 비교(2번째 리스트) + 랜덤 필드값 검사
        assertEquals(expectedList.get(2).toString(),actualList.get(2).toString());
        assertEquals(expectedList.get(2).getPercent(),actualList.get(2).getPercent());
        assertEquals(expectedList.get(2).getRating(),actualList.get(2).getRating());

        verify(mapper).selMockTestResultByDates(dto);
    }

    @Test
    @DisplayName("최고모의고사성적등급테스트 가유저/실유저")
    void getHighestRatingsOfMockTest() {

        //유저1
        Long notExistUserId = 60423L;

        List<StudentSummarySubjectVo> tmpList = new ArrayList<>();
        when(mapper.getHighestRatingsOfMockTest(notExistUserId)).thenReturn(tmpList);
        List<StudentSummarySubjectVo> actualList = mapper.getHighestRatingsOfMockTest(notExistUserId);
        assertTrue(actualList.isEmpty());

        verify(mapper).getHighestRatingsOfMockTest(notExistUserId);


        //유저2
        Long existUserId = 40L;

        //예상 값이 들어있는 리스트 생성
        List<StudentSummarySubjectVo> expList2GetHiRatingsOfMTest = new ArrayList<>();
        expList2GetHiRatingsOfMTest.add(StudentSummarySubjectVo.builder().nm("국어").rating("6").build());
        expList2GetHiRatingsOfMTest.add(StudentSummarySubjectVo.builder().nm("수학").rating("4").build());
        expList2GetHiRatingsOfMTest.add(StudentSummarySubjectVo.builder().nm("영어").rating("6").build());
        expList2GetHiRatingsOfMTest.add(StudentSummarySubjectVo.builder().nm("한국사").rating("6").build());

        when(mapper.getHighestRatingsOfMockTest(existUserId)).thenReturn(expList2GetHiRatingsOfMTest);
        List<StudentSummarySubjectVo> actList2GetHiRatingsOfMTest = mapper.getHighestRatingsOfMockTest(existUserId);

        assertFalse(actList2GetHiRatingsOfMTest.isEmpty());
        //사이즈 비교
        //값비교
        assertEquals(4,actList2GetHiRatingsOfMTest.size());
        assertEquals(actList2GetHiRatingsOfMTest.get(0).toString(),expList2GetHiRatingsOfMTest.get(0).toString());
        assertEquals(expList2GetHiRatingsOfMTest.get(0).getNm(),actList2GetHiRatingsOfMTest.get(0).getNm());
        assertEquals(expList2GetHiRatingsOfMTest.get(1).getNm(),actList2GetHiRatingsOfMTest.get(1).getNm());
        assertEquals(expList2GetHiRatingsOfMTest.get(2).getNm(),actList2GetHiRatingsOfMTest.get(2).getNm());
        assertEquals(expList2GetHiRatingsOfMTest.get(3).getNm(),actList2GetHiRatingsOfMTest.get(3).getNm());

        verify(mapper,times(2)).getHighestRatingsOfMockTest(any());


    }


    @Test
    @DisplayName("최신모의고사성적등급-테스트")
    void getLatestRatingsOfMockTestWithActualUser() {
        //유저1
        Long notExistUserId = 60423L;
        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
        dto.setUserId(notExistUserId);
        dto.setYear("2023");
        dto.setMon("3");

        //유저1 / 3월 이전의 데이터를 조회
        List<StudentTestSumGraphVo> vo = new ArrayList<>();
        when(mapper.getLatestRatingsOfMockTest(dto)).thenReturn(vo);

        vo = mapper.getLatestRatingsOfMockTest(dto);
        assertTrue(vo.isEmpty());


        //유저2 / 타겟일자(현재) 조회
        StudentSummarySubjectDto dto2 = new StudentSummarySubjectDto();
        Long existUserId = 40L;
        dto2.setUserId(existUserId);
        dto2.setYear("2023");
        dto2.setMon("8");
        List<StudentTestSumGraphVo> vo2 = mapper.getLatestRatingsOfMockTest(dto2);
//        assertFalse(vo2.isEmpty());


        //비교값 container
        String date = "2023-6";
        List<StudentSummarySubjectVo> subList = new ArrayList<>();
        subList.add(new StudentSummarySubjectVo("국어","1"));
        subList.add(new StudentSummarySubjectVo("영어","2"));
        subList.add(new StudentSummarySubjectVo("수학","3"));
        subList.add(new StudentSummarySubjectVo("한국사","4"));
        StudentSumContainerVo tmpSsCVo = new StudentSumContainerVo(date, subList);

//        assertFalse(tmpSsCVo.getList().isEmpty());
        /*assertEquals(subList.get(0).toString(),vo2.get(0).toString());*/


        verify(mapper,times(1)).getLatestRatingsOfMockTest(dto2);


    }

    @Test
    @DisplayName("최신모의고사성적등급-테스트")
    void getMockTestGraph() {
        StudentSummarySubjectDto dto3 = new StudentSummarySubjectDto();
        dto3.setUserId(40L);
        dto3.setYear("2023");
        dto3.setMon("8");

        //MAPPER
        List<StudentTestSumGraphVo> vo3 = new ArrayList<>();
        StudentTestSumGraphVo subVo = new StudentTestSumGraphVo();
        subVo.setDate("2023-8");
        subVo.setNm("국어");
        subVo.setRating("8");
        vo3.add(subVo);
        StudentTestSumGraphVo subVo2 = new StudentTestSumGraphVo();
        subVo2.setDate("2023-8");
        subVo2.setNm("수학");
        subVo2.setRating("3");
        vo3.add(subVo2);

        //mapper를 받으면 어떤 타입으로 리턴해줄지 정해주는 파트임
        when(mapper.getMockTestGraph(any())).thenReturn(vo3);

        List<StudentTestSumGraphVo> expectedVo = new ArrayList<>();
        StudentTestSumGraphVo subVo3 = new StudentTestSumGraphVo();
        subVo3.setDate("2023-8");
        subVo3.setNm("국어");
        subVo3.setRating("8");
        expectedVo.add(subVo);
        StudentTestSumGraphVo subVo4 = new StudentTestSumGraphVo();
        subVo4.setDate("2023-8");
        subVo4.setNm("수학");
        subVo4.setRating("3");
        expectedVo.add(subVo2);


        assertFalse(vo3.isEmpty());
        for (int i = 0; i < vo3.size(); i++) {
            assertEquals("2023-8",expectedVo.get(i).getDate());
            assertEquals(vo3.get(i).getNm(),expectedVo.get(i).getNm());
            assertEquals(vo3.get(i).getRating(),expectedVo.get(i).getRating());
        }

        when(service.getMockTestGraph(any())).thenReturn(vo3);
        verify(mapper).getMockTestGraph(dto3);



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