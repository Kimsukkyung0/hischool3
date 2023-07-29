package com.green.secondproject.timetable.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TimeTableVoList {
    private String date;
    private String dayMonToFri;
    private List<TimeTableVo> list;
}
