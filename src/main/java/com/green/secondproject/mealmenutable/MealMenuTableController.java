package com.green.secondproject.mealmenutable;

import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.mealmenutable.model.MealTableContainerVo;
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
@RequestMapping("/api/meal")
@Tag(name = "급식표")
public class MealMenuTableController {
    private final MealMenuTableService service;


//    @Operation(summary = "접속한 달의 급식표출력", description = "요구값 : <br>" + "sdSchulCode - 학교고유코드<br><br>"
//            +"출력값 : <br>" + "(1)schoolNm - 학교명<br>"+"(2)strYearMonth - 접속한 달<br><br>"+
//            "출력값 리스트세부내역 : <br> (1)lunchOrDinner - 중식or석식 <br> (2) menuOftheDay : 식사명(한줄로표시/쉼표로 구분)")
    @GetMapping
    @Operation(summary = "유저별 접속한 달의 급식표출력",
            description = "출력값 : <br>(1)schoolNm - 학교명<br>(2)strYearMonth - 접속한 달<br><br>"+
            "출력값 리스트세부내역 : <br> (1)lunchOrDinner - 중식or석식 <br> (2) menuOftheDay : 식사명(한줄로표시/쉼표로 구분)")
    public MealTableContainerVo getMealTableBySchoolOfTheMonth(@AuthenticationPrincipal MyUserDetails myuser){
        return service.getMealTableBySchoolOfTheMonth(myuser.getSchoolId());
    }

    @GetMapping("/main")
    @Operation(summary = "유저별 접속한 주의 급식표출력", description = "출력값 : <br>" + "(1)schoolNm - 학교명<br>"+"(2)strYearMonth - 접속한 달<br><br>"+
            "출력값 리스트세부내역 : <br> (1)lunchOrDinner - 중식or석식 <br> (2) menuOftheDay : 식사명(한줄로표시/쉼표로 구분)<br><br>")
    public MealTableContainerVo getMealTableBySchoolOfTheWeek(@AuthenticationPrincipal MyUserDetails myuser){
        return service.getMealTableBySchoolOfTheWeek(myuser.getSchoolId());
    }
}
