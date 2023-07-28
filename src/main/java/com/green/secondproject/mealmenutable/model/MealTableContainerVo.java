package com.green.secondproject.mealmenutable.model;

import com.green.secondproject.timetable.model.TimeTableVo;
import lombok.Data;

import java.util.List;

@Data
public class MealTableContainerVo {
    private String schoolNm;
    private List<MealTableVo> list;

    public MealTableContainerVo(String schoolNm,List<MealTableVo> list){
        this.schoolNm = schoolNm;
        this.list = list;
    }
}
