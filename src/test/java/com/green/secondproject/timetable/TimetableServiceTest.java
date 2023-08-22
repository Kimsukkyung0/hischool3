package com.green.secondproject.timetable;

import com.green.secondproject.timetable.model.TimeTableContainerVo;
import com.green.secondproject.timetable.model.TimeTableGetDto;
import com.green.secondproject.timetable.model.TimeTableVo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@Import({TimetableService.class})
class TimetableServiceTest {
    @MockBean
    private TimetableService service;

    @Test
    @DisplayName("GetTimeTableServiceTEST")
    void getTimeTableByClassAndTheWeek() {
        //가짜dto생성
        TimeTableGetDto dto = new TimeTableGetDto();
        dto.setGrade("1");
        dto.setClassNum("1");
        dto.setSchoolNm("오성고등학교");

        //가짜 결과값생성
        List<TimeTableVo> mockSubVo = new ArrayList<>();
        TimeTableVo timeTableListOfOhseongGo = new TimeTableVo();
        timeTableListOfOhseongGo.setDate("20230522");
        timeTableListOfOhseongGo.setDayMonToSun("20230522");//월
        timeTableListOfOhseongGo.setPeriod("1");
        timeTableListOfOhseongGo.setSubject("수학");
        mockSubVo.add(timeTableListOfOhseongGo);
        TimeTableContainerVo mockVo = new TimeTableContainerVo(dto.getSchoolNm(),"1",dto.getGrade(),dto.getSchoolNm(),mockSubVo);

        given(service.getTimeTableByClassAndTheWeek(dto)).willReturn(mockVo);
        //암튼 timetablecontainer vo 형식으로 리턴할 것이다.
    //야믈
        //실제 넘어오는 값
        TimeTableContainerVo result = service.getTimeTableByClassAndTheWeek(dto);

            assertEquals(mockVo.getSchoolNm(), result.getSchoolNm());//학교이름
            assertEquals(mockVo.getGrade(), result.getGrade());//1학년
            assertEquals(mockVo.getClassNm(), result.getClassNm());//1반
            assertEquals(mockVo.getSemester(), result.getSemester());

            //서브 리스트 비교
            TimeTableVo vo0Row = mockVo.getList().get(0);

            assertEquals(vo0Row.getPeriod(), timeTableListOfOhseongGo.getPeriod());
            assertEquals(vo0Row.getSubject(), timeTableListOfOhseongGo.getSubject());
            assertEquals(vo0Row.getDayMonToSun(), timeTableListOfOhseongGo.getDayMonToSun());
            assertEquals(vo0Row.getDate(), timeTableListOfOhseongGo.getDate());
        }
    }


//    @Test
//    @DisplayName("GetTimeTableServiceTEST : 5.22 영진고 1-1(월)")
//    void getTimeTableByClassAndTheWeek2() throws Exception {
//
//        String criterion = "{\"KEY\":\"9a7b0b8f560b480abf0d0cb941af2bea\",\"Type\":\"json\",\"pIndex\": 1,\"pSize\":50" +
//                ",\"ATPT_OFCDC_SC_CODE\":\"D10\",\"SD_SCHUL_CODE\":\"7240099\",\"GRADE\":\"1\",\"classNm\":\"1\",\"TI_FROM_YMD\":\"20230522\",\"TI_TO_YMD\":\"20230526\"}";
//        String url = "https://open.neis.go.kr";
//
//        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("criterion", criterion);
////        ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
////        JsonNode jsonNode =
////
////        Product product = restTemplate.getForObject(builder.build().toUri(), Product.class);
////
////        assertEquals(product.getId(), 1, 0);
//        ResultActions ra = mvc.perform(get("/api/timetable")
//                .contentType(MediaType.APPLICATION_JSON_UTF8));
//
//        ra.andExpect(status().isOk())//200을 기대하면..안되나
//                .andDo(print());
//    }
