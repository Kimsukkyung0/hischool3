package com.green.secondproject.teacher;

import com.green.secondproject.teacher.model.TeacherGraphDto;
import com.green.secondproject.teacher.model.TeacherGraphVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeacherMapperTest {

    @Autowired
    private TeacherMapper mapper;

    String[] cateNms = {"국어", "수학", "영어", "한국사"};
    Long[] cateIdForAca = {1L,3L,6L,7L};
    Long testClassId = 2L;

    //테스트용 날짜데이터
    LocalDate now = LocalDate.now();
    String nowYear = String.valueOf(now.getYear());
    String nowMonth = String.valueOf(now.getMonthValue());
    Long userId = 80L;

    @Test
    @DisplayName("TEST 선생님메인 내신 그래프(mapper)")
    void teacherAcaGraph() {
        List<TeacherGraphVo> expectedList = new ArrayList<>();
        expectedList.add(TeacherGraphVo.builder().cateNm(cateNms[0]).rating(1).percentage(10).build());
        expectedList.add(TeacherGraphVo.builder().cateNm(cateNms[0]).rating(1).percentage(10).build());

        List<TeacherGraphVo> actualList1 = mapper.teacherAcaGraph(testClassId,cateIdForAca[0]);
        assertFalse(actualList1.isEmpty());
        assertEquals(expectedList.get(0).getCateNm(), actualList1.get(0).getCateNm());
        assertEquals(expectedList.get(1).getCateNm(), actualList1.get(1).getCateNm());


        List<TeacherGraphVo> expectedList2 = new ArrayList<>();
        expectedList2.add(TeacherGraphVo.builder().cateNm(cateNms[1]).rating(2).percentage(10).build());
        expectedList2.add(TeacherGraphVo.builder().cateNm(cateNms[1]).rating(2).percentage(10).build());

        List<TeacherGraphVo> actualList2 = mapper.teacherAcaGraph(testClassId,cateIdForAca[0]);
        assertFalse(actualList2.isEmpty());
        assertEquals(expectedList2.get(0).getCateNm(), actualList2.get(0).getCateNm());
        assertEquals(expectedList2.get(1).getCateNm(), actualList2.get(1).getCateNm());
    }

    @Test
    @DisplayName("TEST 주요과목별 응시 학생수")
    void getNumberOfStudentByCate() {
        double expectedStudentNum = 10.0;
        List<TeacherGraphDto> list = new ArrayList<>();
        List<Double> actualStudentNum = new ArrayList<>();

        for (int i = 0; i < cateIdForAca.length; i++) {
            list.add(TeacherGraphDto.builder().categoryId(cateIdForAca[i]).classId(testClassId).build());
            actualStudentNum.add(mapper.getNumberOfStudentByCate(list.get(i)));
        }

        assertFalse(actualStudentNum.isEmpty());
        assertTrue(actualStudentNum.contains(anyDouble()));
        assertEquals(cateIdForAca.length,actualStudentNum.size());
    }

    @Test
    @DisplayName("TEST 최근응시시험")
    void getLatestTest() {
        String expectedDate = "";
        String result = mapper.getLatestTest();
        
    }
}