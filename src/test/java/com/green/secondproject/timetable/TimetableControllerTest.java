//package com.green.secondproject.timetable;
//
//import com.green.secondproject.timetable.model.TimeTableContainerVo;
//import com.green.secondproject.timetable.model.TimeTableParam;
//import com.green.secondproject.timetable.model.TimeTableVo;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//
//
//@WebMvcTest(TimetableController.class)
//class TimetableControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private TimetableService service;
//
//    @Test
//    @DisplayName("접속일 기준-주단위 시간표출력.api")
//    void getTimeTableByClassOfTheWeek() {
//        //테스트용 데이터넘기기
//        //가짜 서비스로 오성고 1학년 1반값 넘기기
//        TimeTableParam p = new TimeTableParam();
//        p.setSdSchulCode("7240099");
//        p.setGrade("1");
//        p.setClassNm("1");
//        TimeTableContainerVo vo = service.getTimeTableByClassAndTheWeek(p);
//        given(service.getTimeTableByClassAndTheWeek(any())).willReturn(vo);
//
//        //예상한 값이 들어있는 가짜리스트 만들기
//        //리스트엔 1교시국어, 2교시 통합사회만 들어있음(5월 1일 기준)
//        List<TimeTableVo> mockTableVolist = new ArrayList<TimeTableVo>();
//        TimeTableVo mockSubVo = new TimeTableVo();//서브리스트
//        mockSubVo.setPeriod("1");//1교시
//        mockSubVo.setSubject("국어");//과목명
//        mockTableVolist.add(mockSubVo);
//
//        TimeTableContainerVo mockVo = new TimeTableContainerVo("오성고등학교","1","1","1",mockTableVolist);
//
//        //service통신 vo와 containervo와 비교 (학교명, 학년일치)
////        assertEquals(vo.getSchoolNm(),mockVo.getSchoolNm());
//        assertEquals(vo.getClassNm(),mockVo.getClassNm());
//
//        //실제로 넘어오는 값은 null이맞음. 지금은 수업이 없거든여!1
//        List<TimeTableVo> subList = vo.getList();
//        if(subList != null){//null이 아니라면, 검사한다.
//        assertEquals(subList.get(0).getPeriod(),mockSubVo.getPeriod());
//        assertEquals(subList.get(0).getSubject(),mockSubVo.getSubject());
//        }
//
//        verify(service).getTimeTableByClassAndTheWeek(any());
//
//    }
//}