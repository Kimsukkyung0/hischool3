package com.green.secondproject.timetable;

import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.timetable.model.TimeTableContainerVo;
import com.green.secondproject.timetable.model.TimeTableParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/timetable")
@Tag(name = "시간표")
public class TimetableController {
    private final TimetableService service;

    @GetMapping
    @Operation(summary = "접속한 주의 시간표출력(월-금)", description = "요구값 : <br>" + "(1)sdSchulCode - 학교고유코드<br>"+"(2)grade - 학년 <br>"+
            "classNm:학급번호<br><br>" +"출력값 : <br>" + "(1)schoolNm - 학교명<br>"+"(2)grade - 학년 <br>"+
            "(3)classNm:학급번호<br>"+"(4)grade - 학년 <br>"+"(5)semester - 학기<br><br> "+"출력값 리스트세부내역 : <br> (1)dayMonToSun - 요일정보<br> (2)date : 시간표기준일(0:월 1:화 2:수 3:목 4:금 5:토 6:일) <br> (3)period - 교시 <br> (4) class_contents : 수업명<br>"
            +
            "※ 7월 둘째주 부로 고등학교가 방학기간이라 개발단계에서는 주단위 테스트시 날짜값이 5.22-5.26일로 고정되어있습니다.※<br>")
    public TimeTableContainerVo GetTimeTableByClassOfTheWeek(TimeTableParam timeTableParam){
        log.info("timetableParam : {}",timeTableParam);
        return service.getTimeTableByClassAndTheWeek(timeTableParam);
    }


//    @GetMapping
//    @Operation(summary = "접속한 주의 시간표출력(월-금)", description = "요구값 : <br>" + "(1)sdSchulCode - 학교고유코드<br>"+"(2)grade - 학년 <br>"+
//            "classNm:학급번호<br><br>" +"출력값 : <br>" + "(1)schoolNm - 학교명<br>"+"(2)grade - 학년 <br>"+
//            "(3)classNm:학급번호<br>"+"(4)grade - 학년 <br>"+"(5)semester - 학기<br><br> "+"출력값 리스트세부내역 : <br> (1)dayMonToSun - 요일정보<br> (2)date : 시간표기준일(0:월 1:화 2:수 3:목 4:금 5:토 6:일) <br> (3)period - 교시 <br> (4) class_contents : 수업명<br>"
//            +
//            "※ 7월 둘째주 부로 고등학교가 방학기간이라 개발단계에서는 주단위 테스트시 날짜값이 5.22-5.26일로 고정되어있습니다.※<br>")
//    public TimeTableContainerVo GetTimeTableByClassOfTheWeek(@AuthenticationPrincipal MyUserDetails myUserDetails){
//        log.info("myUserDetails.id  : {}",myUserDetails.getUserId());
//        log.info("myUserDetails.name  : {}",myUserDetails.getUsername());
//
//        return service.getTimeTableByClassAndTheWeek(myUserDetails);
//    }

    //현재일자기준으로 그 주의 월~금 시간표 표출
/*KEY	  STRING(필수)	  인증키	  기본값 : sample key
    Type	  STRING(필수)	  호출 문서(xml, json)	  기본값 : xml
    pIndex	  INTEGER(필수)	  페이지 위치	  기본값 : 1(sample key는 1 고정)
    pSize	  INTEGER(필수)	  페이지 당 신청 숫자	  기본값 : 100(sample key는 5 고정)*/
}
