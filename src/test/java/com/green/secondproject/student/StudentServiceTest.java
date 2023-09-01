//package com.green.secondproject.student;
//
//import com.green.secondproject.mealmenutable.MealMenuTableService;
//import com.green.secondproject.student.model.*;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Spy;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Description;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.assertj.core.api.BDDAssumptions.given;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(SpringExtension.class)
//@Import({StudentMapper.class,StudentService.class})
//class StudentServiceTest {
//@MockBean
//StudentMapper mapper;
//@MockBean
//StudentService service;
//
//    @Test
//    @Disabled
//    @DisplayName("연월에따른 모의고사점수조회(+가짜유저)")
//    void FakeListTestForMockTestResultByDates() {
//        //학생test1
//        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
//        dto.setUserId(603L);
//        dto.setYear("2023");
//
//        //test용 가짜 list생성
//        List<StudentMockSumResultVo>fakeList = new ArrayList<>();
//        fakeList.add(StudentMockSumResultVo.builder()
//                .year("2023")
//                .mon("8")
//                .cateName("과학")
//                .nm("4")
//                .standardScore("42")
//                .rating("8")
//                .percent("42")
//                .build());
//        when(mapper.selMockTestResultByDates(dto)).thenReturn(fakeList);
//
//        //실제 리스트 받아오는 작업(비교용)
//        List<StudentMockSumResultVo> actualList = mapper.selMockTestResultByDates(dto);
//
//        //비어있지 않은 값을 기대한다.
//        assertFalse(actualList.isEmpty());
//        //가짜리스트를 포함하고 있지 않을 것이다.
//        assertFalse(actualList.contains(fakeList));
//        //그러나, 요청한 연도의 값은 같다.
//        assertEquals(Integer.parseInt(fakeList.get(0).getYear()),Integer.parseInt(actualList.get(0).getYear()));
//        //"과학"이라는 카테고리 이름은 포함하고 있지않을것이다.
//        assertFalse(actualList.contains(fakeList.get(0).getCateName()));
//
//
//        verify(mapper).selMockTestResultByDates(dto);
//
//    }
//
//    @Test
//    @Disabled
//    @DisplayName("연월에따른 모의고사점수조회(+실유저)")
//    void ExpectedListTestForMockTestResultByDates() {
//        //예상값과 일치하는 리스트생성
//        List<StudentMockSumResultVo> expectedList = new ArrayList<>();
//        expectedList.add(StudentMockSumResultVo.builder()
//                .year("2023")
//                .mon("6")
//                .cateName("국어")
//                .nm("국어")
//                .standardScore("70")
//                .rating("6")
//                .percent("67")
//                .build());
//        expectedList.add(StudentMockSumResultVo.builder()
//                .year("2023")
//                .mon("6")
//                .cateName("수학")
//                .nm("수학")
//                .standardScore("80")
//                .rating("6")
//                .percent("67")
//                .build());
//        expectedList.add(StudentMockSumResultVo.builder()
//                .year("2023")
//                .mon("6")
//                .cateName("영어")
//                .nm("영어")
//                .standardScore("80")
//                .rating("6")
//                .percent("67")
//                .build());
//        //예상키값
//        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
//        dto.setUserId(40L);
//        dto.setYear("2023");
//
//        //실제 리스트를 받아오는 작업
//        when(mapper.selMockTestResultByDates(dto)).thenReturn(expectedList);
//        List<StudentMockSumResultVo> actualList = mapper.selMockTestResultByDates(dto);
//
//        //not null check
//        assertFalse(actualList.isEmpty());
//        //실제로 값이 일치하는지 비교(0번째 리스트)
//        assertEquals(Integer.parseInt(expectedList.get(0).getYear()),Integer.parseInt(actualList.get(0).getYear()));
//        assertEquals(Integer.parseInt(expectedList.get(0).getMon()),Integer.parseInt(actualList.get(0).getMon()));
//        assertEquals(expectedList.get(0).getCateName(),actualList.get(0).getCateName());
//        assertEquals(expectedList.get(0).toString(),actualList.get(0).toString());
//
//        ////값 비교(1번째 리스트) + 랜덤 필드값 검사
//        assertEquals(expectedList.get(1).toString(),actualList.get(1).toString());
//        assertEquals(expectedList.get(1).getPercent(),actualList.get(1).getPercent());
//
//        ////값 비교(2번째 리스트) + 랜덤 필드값 검사
//        assertEquals(expectedList.get(2).toString(),actualList.get(2).toString());
//        assertEquals(expectedList.get(2).getPercent(),actualList.get(2).getPercent());
//        assertEquals(expectedList.get(2).getRating(),actualList.get(2).getRating());
//
//        verify(mapper).selMockTestResultByDates(dto);
//    }
//
//    @Test
//    @DisplayName("최고모의고사성적등급테스트 가유저/실유저")
//    void getHighestRatingsOfMockTest() {
//
//        //유저1
//        Long notExistUserId = 60423L;
//
//        List<StudentSummarySubjectVo> tmpList = new ArrayList<>();
//        when(mapper.getHighestRatingsOfMockTest(notExistUserId)).thenReturn(tmpList);
//        List<StudentSummarySubjectVo> actualList = mapper.getHighestRatingsOfMockTest(notExistUserId);
//        assertTrue(actualList.isEmpty());
//
//        verify(mapper).getHighestRatingsOfMockTest(notExistUserId);
//
//
//        //유저2
//        Long existUserId = 40L;
//
//        //예상 값이 들어있는 리스트 생성
//        List<StudentSummarySubjectVo> expList2GetHiRatingsOfMTest = new ArrayList<>();
//        expList2GetHiRatingsOfMTest.add(StudentSummarySubjectVo.builder().nm("국어").rating("6").build());
//        expList2GetHiRatingsOfMTest.add(StudentSummarySubjectVo.builder().nm("수학").rating("4").build());
//        expList2GetHiRatingsOfMTest.add(StudentSummarySubjectVo.builder().nm("영어").rating("6").build());
//        expList2GetHiRatingsOfMTest.add(StudentSummarySubjectVo.builder().nm("한국사").rating("6").build());
//
//        when(mapper.getHighestRatingsOfMockTest(existUserId)).thenReturn(expList2GetHiRatingsOfMTest);
//        List<StudentSummarySubjectVo> actList2GetHiRatingsOfMTest = mapper.getHighestRatingsOfMockTest(existUserId);
//
//        assertFalse(actList2GetHiRatingsOfMTest.isEmpty());
//        //사이즈 비교
//        //값비교
//        assertEquals(4,actList2GetHiRatingsOfMTest.size());
//        assertEquals(actList2GetHiRatingsOfMTest.get(0).toString(),expList2GetHiRatingsOfMTest.get(0).toString());
//        assertEquals(expList2GetHiRatingsOfMTest.get(0).getNm(),actList2GetHiRatingsOfMTest.get(0).getNm());
//        assertEquals(expList2GetHiRatingsOfMTest.get(1).getNm(),actList2GetHiRatingsOfMTest.get(1).getNm());
//        assertEquals(expList2GetHiRatingsOfMTest.get(2).getNm(),actList2GetHiRatingsOfMTest.get(2).getNm());
//        assertEquals(expList2GetHiRatingsOfMTest.get(3).getNm(),actList2GetHiRatingsOfMTest.get(3).getNm());
//
//        verify(mapper,times(2)).getHighestRatingsOfMockTest(any());
//
//
//    }
//
//
//    @Test
//    @DisplayName("최신모의고사성적등급-테스트")
//    void getLatestRatingsOfMockTestWithActualUser() {
//        //유저1
//        Long notExistUserId = 60423L;
//        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
//        dto.setUserId(notExistUserId);
//        dto.setYear("2023");
//        dto.setMon("3");
//
//        //유저1 / 3월 이전의 데이터를 조회
//        List<StudentTestSumGraphVo> vo = new ArrayList<>();
//        when(mapper.getLatestRatingsOfMockTest(dto)).thenReturn(vo);
//
//        vo = mapper.getLatestRatingsOfMockTest(dto);
//        assertTrue(vo.isEmpty());
//
//
//        //유저2 / 타겟일자(현재) 조회
//        StudentSummarySubjectDto dto2 = new StudentSummarySubjectDto();
//        Long existUserId = 40L;
//        dto2.setUserId(existUserId);
//        dto2.setYear("2023");
//        dto2.setMon("8");
//        List<StudentTestSumGraphVo> vo2 = mapper.getLatestRatingsOfMockTest(dto2);
////        assertFalse(vo2.isEmpty());
//
//
//        //비교값 container
//        String date = "2023-6";
//        List<StudentSummarySubjectVo> subList = new ArrayList<>();
//        subList.add(new StudentSummarySubjectVo("국어","1"));
//        subList.add(new StudentSummarySubjectVo("영어","2"));
//        subList.add(new StudentSummarySubjectVo("수학","3"));
//        subList.add(new StudentSummarySubjectVo("한국사","4"));
//        StudentSumContainerVo tmpSsCVo = new StudentSumContainerVo(date, subList);
//
////        assertFalse(tmpSsCVo.getList().isEmpty());
//        /*assertEquals(subList.get(0).toString(),vo2.get(0).toString());*/
//
//
//        verify(mapper,times(1)).getLatestRatingsOfMockTest(dto2);
//
//
//    }
//
//    @Test
//    @DisplayName("최신모의고사성적등급-테스트")
//    void getMockTestGraph() {
//        StudentSummarySubjectDto dto3 = new StudentSummarySubjectDto();
//        dto3.setUserId(40L);
//        dto3.setYear("2023");
//        dto3.setMon("8");
//
//        //MAPPER
//        List<StudentTestSumGraphVo> vo3 = new ArrayList<>();
//        StudentTestSumGraphVo subVo = new StudentTestSumGraphVo();
//        subVo.setDate("2023-8");
//        subVo.setNm("국어");
//        subVo.setRating("8");
//        vo3.add(subVo);
//        StudentTestSumGraphVo subVo2 = new StudentTestSumGraphVo();
//        subVo2.setDate("2023-8");
//        subVo2.setNm("수학");
//        subVo2.setRating("3");
//        vo3.add(subVo2);
//
//        //mapper를 받으면 어떤 타입으로 리턴해줄지 정해주는 파트임
//        when(mapper.getMockTestGraph(any())).thenReturn(vo3);
//
//        List<StudentTestSumGraphVo> expectedVo = new ArrayList<>();
//        StudentTestSumGraphVo subVo3 = new StudentTestSumGraphVo();
//        subVo3.setDate("2023-8");
//        subVo3.setNm("국어");
//        subVo3.setRating("8");
//        expectedVo.add(subVo);
//
//        StudentTestSumGraphVo subVo4 = new StudentTestSumGraphVo();
//        subVo4.setDate("2023-8");
//        subVo4.setNm("수학");
//        subVo4.setRating("3");
//        expectedVo.add(subVo2);
//
//
//        assertFalse(vo3.isEmpty());
//        for (int i = 0; i < vo3.size(); i++) {
//            assertEquals("2023-8",expectedVo.get(i).getDate());
//            assertEquals(vo3.get(i).getNm(),expectedVo.get(i).getNm());
//            assertEquals(vo3.get(i).getRating(),expectedVo.get(i).getRating());
//        }
//
//    }
//
//    @Test
//    @DisplayName("내신성적조회TEST- 기간별")
//    void selAcaTestResultByDatesAndPeriod() {
//        //유저값+year+semester + midOrFin
//        StudentAcaResultsParam param = new StudentAcaResultsParam();
//        param.setSemester(1);
//        param.setYear("2023");
//        param.setUserId(40L);
//        param.setMidFinal(1);
//
//        List<StudentAcaResultVo> mockVoList = new ArrayList<>();
//        mockVoList.add(StudentAcaResultVo.builder().year("2023").semester(1).midFinal(1).cateName("국어").build());
//        mockVoList.add(StudentAcaResultVo.builder().year("2023").semester(1).midFinal(1).cateName("수학").build());
//        mockVoList.add(StudentAcaResultVo.builder().year("2023").semester(1).midFinal(1).cateName("영어").build());
//        mockVoList.add(StudentAcaResultVo.builder().year("2023").semester(1).midFinal(1).cateName("한국사").build());
//
//
//        when(service.selAcaTestResultByDatesAndPeriod(param)).thenReturn(mockVoList);
//
//        assertEquals(param.getSemester(),mockVoList.get(0).getSemester());
//        assertEquals(param.getYear(),mockVoList.get(1).getYear());
//        assertEquals(param.getMidFinal(),mockVoList.get(2).getMidFinal());
//
//        service.selAcaTestResultByDatesAndPeriod(param);
//        verify(service,times(1)).selAcaTestResultByDatesAndPeriod(param);
//
//    }
//
//    @Test
//    void getHighestRatingsOfAcaTest() {
//        //요구값
//        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
//        dto.setUserId(1L);
//        dto.setYear("2023");
//        dto.setMon("6");
//
//        //리턴되어야 하는 리스트
//        List<StudentSummarySubjectVo> mockVoList = new ArrayList<>();
//        mockVoList.add(StudentSummarySubjectVo.builder().nm("국어").rating("1").build());
//        mockVoList.add(StudentSummarySubjectVo.builder().nm("수학").rating("2").build());
//        mockVoList.add(StudentSummarySubjectVo.builder().nm("영어").rating("3").build());
//        mockVoList.add(StudentSummarySubjectVo.builder().nm("한국사").rating("4").build());
//
//        //예상리스트
//        List<StudentSummarySubjectVo> expecVoList = new ArrayList<>();
//        expecVoList.add(StudentSummarySubjectVo.builder().nm("국어").rating("1").build());
//        expecVoList.add(StudentSummarySubjectVo.builder().nm("수학").rating("2").build());
//        expecVoList.add(StudentSummarySubjectVo.builder().nm("영어").rating("3").build());
//        expecVoList.add(StudentSummarySubjectVo.builder().nm("한국사").rating("4").build());
//
//        for (int i = 0; i < expecVoList.size(); i++) {
//            assertEquals(expecVoList.get(i).toString(),mockVoList.get(i).toString());
//            assertEquals(expecVoList.get(i).getNm(),mockVoList.get(i).getNm());
//        }
//
////        public List<StudentSummarySubjectVo> getHighestRatingsOfAcaTest(StudentSummarySubjectDto dto) {
////            LocalDate now = LocalDate.now();
////            dto.setYear(String.valueOf(now.getYear()));
////            return mapper.getHighestRatingsOfAcaTest(dto);
////        }
//    }
//
//    @Test
//    @Disabled
//    void getLatestRatingsOfAcaTest() {
//        List<StudentSummarySubjectVo> tmp = new ArrayList<>();
//        tmp.add(StudentSummarySubjectVo.builder().nm("국어").rating("1").build());
//        tmp.add(StudentSummarySubjectVo.builder().nm("영어").rating("2").build());
//        tmp.add(StudentSummarySubjectVo.builder().nm("수학").rating("3").build());
//        tmp.add(StudentSummarySubjectVo.builder().nm("한국사").rating("4").build());
//
//        Long userId = 10L;
//
//        String date = "2023-2 기말";
//
//        StudentSumContainerVo vo = new StudentSumContainerVo(date,tmp);
//        assertEquals(date,vo.getDate());
//        assertEquals(tmp.get(0).getNm(),vo.getList().get(0).getNm());
//        assertEquals(tmp.get(0).getRating(),vo.getList().get(0).getRating());
//        assertEquals(tmp.get(1).getNm(),vo.getList().get(1).getNm());
//        assertEquals(tmp.get(1).getRating(),vo.getList().get(1).getRating());
//        assertEquals(tmp.get(2).getNm(),vo.getList().get(2).getNm());
//        assertEquals(tmp.get(2).getRating(),vo.getList().get(2).getRating());
//        assertEquals(tmp.get(3).getNm(),vo.getList().get(3).getNm());
//        assertEquals(tmp.get(3).getRating(),vo.getList().get(3).getRating());
//
//        when(service.getLatestRatingsOfAcaTest(userId)).thenReturn(vo);
//
//    }
//
//    @Test
//    void getAcaTestGraph() {
//        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
//        dto.setYear("2023");
//        dto.setMon("6");
//        dto.setUserId(142L);
//
//        List<StudentTestSumGraphVo> result = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            StudentTestSumGraphVo subResult = new StudentTestSumGraphVo();
//            subResult.setRating(String.valueOf(i+1));
//            subResult.setNm("국어");
//            subResult.setDate("2023-2 중간");
//            result.add(subResult);
//        }
//        when(service.getAcaTestGraph(dto)).thenReturn(result);
//        assertEquals(dto.getYear(),result.get(0).getDate().substring(0,4));
//        assertEquals(dto.getYear(),result.get(1).getDate().substring(0,4));
//        assertEquals(dto.getYear(),result.get(2).getDate().substring(0,4));
//    }
//
//    @Test
//    @DisplayName("내신날짜형식메서드 TEST")
//    void getMidFinalFormOfDate() {
//
//        String date1 = "202322";
//        String ResultDate1 = "2023-2 기말";
//        when(service.getMidFinalFormOfDate(date1)).thenReturn(ResultDate1);
//        assertEquals("2023-2 기말",ResultDate1);
//
//        String date2 = "202321";
//        String ResultDate2 = "2023-2 중간";
//        when(service.getMidFinalFormOfDate(date2)).thenReturn(ResultDate2);
//        assertEquals("2023-2 중간",ResultDate2);
//
//        String date3 = "202311";
//        String ResultDate3 = "2023-1 중간";
//        when(service.getMidFinalFormOfDate(date3)).thenReturn(ResultDate3);
//        assertEquals("2023-1 중간",ResultDate3);
//
//        String date4 = "202312";
//        String ResultDate4 = "2023-1 기말";
//        when(service.getMidFinalFormOfDate(date4)).thenReturn(ResultDate4);
//        assertEquals("2023-1 기말",ResultDate4);
//
//    }
//}