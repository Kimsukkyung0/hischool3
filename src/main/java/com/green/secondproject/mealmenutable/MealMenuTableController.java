package com.green.secondproject.mealmenutable;

import com.green.secondproject.mealmenutable.model.MealTableContainerVo;
import com.green.secondproject.mealmenutable.model.MealTableParam;
import com.green.secondproject.timetable.model.TimeTableParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    @GetMapping
    public MealTableContainerVo GetMealTableBySchoolOfTheWeek(MealTableParam p){
        log.info("timetableParam : {}",p);
        return service.GetMealTableBySchoolOfTheWeek(p);
    }
}
