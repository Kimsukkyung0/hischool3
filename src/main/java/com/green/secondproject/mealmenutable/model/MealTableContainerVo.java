package com.green.secondproject.mealmenutable.model;

import com.green.secondproject.timetable.model.TimeTableVo;
import lombok.Data;

import java.util.List;

@Data
public class MealTableContainerVo {
    private String schoolNm;
    private String strYearMonth;
    private List<MealTableVo> list;

    public MealTableContainerVo(String schoolNm,String strYearMonth,List<MealTableVo> list){
        this.schoolNm = schoolNm;
        this.strYearMonth = strYearMonth;
        this.list = list;
    }
}
