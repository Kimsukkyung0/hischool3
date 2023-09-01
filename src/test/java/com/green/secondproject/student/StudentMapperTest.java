//package com.green.secondproject.student;
//
//import com.green.secondproject.student.model.*;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//@MybatisTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class StudentMapperTest {
//    @Autowired
//    private StudentMapper mapper;
//
//
//    String[] cateNms = {"국어", "수학", "영어", "한국사"};
//    LocalDate now = LocalDate.now();
//    Long fkUserId = 10000L;
//
//    @Test
//    @DisplayName("학생메인 내신그래프 mapper TEST")
//    void selMockTestResultByDates() {
//        //유저 Id만 넘겼을때
//        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
//        dto.setUserId(1L);
//
//        List<StudentMockSumResultVo> expectedList = new ArrayList<>();
//        expectedList.add(StudentMockSumResultVo.builder().year("2023").mon("9").cateName("수학").nm("수학").standardScore("90").rating("3").percent("30").build());
//        expectedList.add(StudentMockSumResultVo.builder().year("2023").mon("12").cateName("국어").nm("화법과언어").standardScore("0").rating("3").percent("30").build());
//
//        List<StudentMockSumResultVo> actualList = mapper.selMockTestResultByDates(dto);
//
//        assertEquals(expectedList.get(0).getYear(), actualList.get(0).getYear());
//        assertEquals(expectedList.get(0).toString(), actualList.get(0).toString());
//
//
//        assertEquals(expectedList.get(1).getYear(), actualList.get(1).getYear());
//
//        //월정보까지 넘겼을때
//
//        dto.setMon("6");
//        List<StudentMockSumResultVo> actualList2 = mapper.selMockTestResultByDates(dto);
//        assertNotEquals(expectedList.size(), actualList.size());
//        assertEquals(expectedList.get(0).getYear(), actualList.get(0).getYear());
//        assertEquals(expectedList.get(0).getMon(), actualList.get(0).getMon());
//        assertEquals(expectedList.get(0).getNm(), actualList.get(0).getNm());
//
//    }
//
//    @Test
//    @DisplayName("유저별 모의고사점수 최고 성적 TEST")
//    void getHighestRatingsOfMockTest() {
//        //없는 유저값 보내기
//        List<StudentSummarySubjectVo> testList = mapper.getHighestRatingsOfMockTest(fkUserId);
//        assertTrue(testList.isEmpty());
//
//        //실유저 값 보내기
//        Long rlUserId = 2L;
//        List<StudentSummarySubjectVo> testList2 = mapper.getHighestRatingsOfMockTest(rlUserId);
//        List<StudentSummarySubjectVo> testList3 = new ArrayList<>();
//        for (int i = 0; i < cateNms.length; i++) {
//            testList3.add(StudentSummarySubjectVo.builder().nm(cateNms[i]).rating(String.valueOf(i+1)).build());
//        }
//        assertFalse(testList2.isEmpty());
//        assertEquals(testList3.size(), testList2.size());
//        for (int i = 0; i < cateNms.length; i++) {
//            assertEquals(cateNms[i], testList2.get(i).getNm());
//        }
//
//    }
//
//    @Test
//    @DisplayName("유저별 최근모의고사 성적 TEST")
//    void getLatestRatingsOfMockTest() {
//        //없는 유저값 보내기
//        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
//        dto.setUserId(fkUserId);
//        dto.setYear(String.valueOf(now.getYear()));
//        dto.setMon(String.valueOf(now.getMonthValue()));
//        List<StudentTestSumGraphVo> testList = mapper.getLatestRatingsOfMockTest(dto);
//        assertTrue(testList.isEmpty());
//
//        //실유저 값 보내기
//        dto.setUserId(3L);
//        String Date = "20233";
//        List<StudentTestSumGraphVo> testList2 = mapper.getLatestRatingsOfMockTest(dto);
//        List<StudentTestSumGraphVo> testList3 = new ArrayList<>();
//        for (int i = 0; i < cateNms.length; i++) {
//            StudentTestSumGraphVo subList = new StudentTestSumGraphVo();
//            subList.setDate(Date);
//            subList.setNm(cateNms[i]);
//            subList.setRating(String.valueOf(i + 2));
//            testList3.add(subList);
//        }
//        System.out.println(testList2.toString());
//        assertFalse(testList2.isEmpty());
//        for (int i = 0; i < cateNms.length; i++) {
//            assertEquals(cateNms[i], testList3.get(i).getNm());
//            assertEquals(Date, testList2.get(i).getDate());
//        }
//    }
//
//    @Test
//    @DisplayName("모의고사 성적그래프 (연간)TEST")
//    void getMockTestGraph() {
//        //존재하지않는 유저값
//        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
//        dto.setUserId(fkUserId);
//        LocalDate now = LocalDate.now();
//        dto.setYear(String.valueOf(now.getYear()));
//        dto.setMon(String.valueOf(now.getMonthValue()));
//        List<StudentTestSumGraphVo> testList = mapper.getMockTestGraph(dto);
//        assertTrue(testList.isEmpty());
//
//        //존재하는 유저값
//        dto.setUserId(3L);
//        String Date = "202303";
//        List<StudentTestSumGraphVo> testList2 = mapper.getMockTestGraph(dto);
//        List<StudentTestSumGraphVo> testList3 = new ArrayList<>();
//        for (int i = 0; i < cateNms.length; i++) {
//            StudentTestSumGraphVo subList = new StudentTestSumGraphVo();
//            subList.setDate(Date);
//            subList.setNm(cateNms[i]);
//            subList.setRating(String.valueOf(i + 2));
//            testList3.add(subList);
//        }
//        assertFalse(testList2.isEmpty());
//        for (int i = 0; i < cateNms.length; i++) {
//            assertEquals(cateNms[i], testList3.get(i).getNm());
//            assertEquals(Date, testList2.get(i).getDate());
//        }
//    }
//
//    @Test
//    void selAcaTestResultByDatesAndPeriod() {
//        //유저값만 넘겼을때
//        Long fkUserId = 2L;
//        StudentAcaResultsParam param = new StudentAcaResultsParam();
//        param.setYear(String.valueOf(now.getYear()));
//        param.setSemester(1);
//        param.setMidFinal(1);
//        param.setUserId(fkUserId);
//        //주요과목
//
//        //실제 mapper로 부터 넘어오는 값
//        List<StudentAcaResultVo> testList1 = mapper.selAcaTestResultByDatesAndPeriod(param);
//        assertFalse(testList1.isEmpty());
//        //날짜설정
//        List<StudentAcaResultVo> compList = new ArrayList<>();
//        for (int i = 0; i < cateNms.length; i++) {
//            compList.add(StudentAcaResultVo.builder()
//                    .cateName(cateNms[i])
//                    .nm(cateNms[i])
//                    .year(param.getYear())
//                    .midFinal(1)
//                    .semester(1)
//                    .rating(i+1)
//                    .classRank(i*10)
//                    .wholeRank(i*20)
//                    .build());
//        }
//
//        for (int i = 0; i < testList1.size(); i++) {
//            assertEquals(compList.get(i).getYear(),testList1.get(i).getYear());
//            assertEquals(compList.get(i).getMidFinal(),testList1.get(i).getMidFinal());
//            assertEquals(compList.get(i).getSemester(),testList1.get(i).getSemester());
//        }
//    }
//
//    @Test
//    void getHighestRatingsOfAcaTest() {
//        //없는 값
//        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
//        dto.setUserId(fkUserId);
//        dto.setYear(String.valueOf(now.getYear()));
//        dto.setMon(String.valueOf(now.getMonthValue()));
//        List<StudentSummarySubjectVo> testList = mapper.getHighestRatingsOfAcaTest(dto);
//        assertTrue(testList.isEmpty());
//
//        //존재하는 값
//        dto.setUserId(2L);
//        List<StudentSummarySubjectVo> testList2 = mapper.getHighestRatingsOfAcaTest(dto);
//        assertFalse(testList2.isEmpty());
//
//        List<StudentSummarySubjectVo> compList = new ArrayList<>();
//        assertEquals(cateNms.length,testList2.size());
//        for (int i = 0; i < cateNms.length; i++) {
//            compList.add(StudentSummarySubjectVo.builder().nm(cateNms[i]).rating(String.valueOf(i+1)).build());
//            assertEquals(compList.get(i).getNm(),testList2.get(i).getNm());
//        }
//    }
//
//    @Test
//    void getLatestRatingsOfAcaTest() {
//        List<StudentTestSumGraphVo> testList = mapper.getLatestRatingsOfAcaTest(fkUserId);
//        assertTrue(testList.isEmpty());
//
//        List<StudentTestSumGraphVo> testList2 = mapper.getLatestRatingsOfAcaTest(2L);
//        assertTrue(!testList2.isEmpty());
//
//        String date = "20232";
//        List<StudentTestSumGraphVo> testList3 = new ArrayList<>();
//        for (int i = 0; i < cateNms.length; i++) {
//            StudentTestSumGraphVo subList = new StudentTestSumGraphVo();
//            subList.setDate(date);
//            subList.setNm(cateNms[i]);
//            subList.setRating(String.valueOf(i + 2));
//            testList3.add(subList);
//        }
//
//        assertEquals(cateNms.length,testList2.size());
//
//    }
//
//    @Test
//    void getAcaTestGraph() {
//        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
//        dto.setUserId(fkUserId);
//        dto.setYear(String.valueOf(now.getYear()));
//        dto.setMon(String.valueOf(now.getMonthValue()));
//        List<StudentTestSumGraphVo> testList = mapper.getAcaTestGraph(dto);
//        assertTrue(testList.isEmpty());
//
//        dto.setUserId(2L);
//        List<StudentTestSumGraphVo> testList2 = mapper.getAcaTestGraph(dto);
//        assertTrue(!testList2.isEmpty());
//
//        String date = "20232";
//        List<StudentTestSumGraphVo> testList3 = new ArrayList<>();
//        for (int i = 0; i < cateNms.length; i++) {
//            StudentTestSumGraphVo subList = new StudentTestSumGraphVo();
//            subList.setDate(date);
//            subList.setNm(cateNms[i]);
//            subList.setRating(String.valueOf(i + 2));
//            testList3.add(subList);
//        }
//        assertEquals(15,testList2.size());
//    }
//
//
////    List<StudentSummarySubjectVo> getHighestRatingsOfAcaTest(StudentSummarySubjectDto dto);
////    List<StudentTestSumGraphVo> getLatestRatingsOfAcaTest(Long userId);
////    List<StudentTestSumGraphVo> getAcaTestGraph(StudentSummarySubjectDto dto);
//
//}
//
//
