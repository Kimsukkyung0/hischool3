//package com.green.secondproject.timetable;
//
//import com.green.secondproject.timetable.model.TimeTableParam;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
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
//        TimeTableParam p = new TimeTableParam();
//        p.setSdSchulCode("7240099");//학교고유코드 - 오성고
//        p.setGrade("1");
//        p.setClassNm("2");//오성고 1학년 2반의 시간표 조회
//
//
//        verify(service).getTimeTableByClassAndTheWeek(any());
//
//    }
//}