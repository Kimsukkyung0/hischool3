package com.green.secondproject.timetable.model;

import lombok.Data;

@Data
public class TimeTableGetDto {
    private Long schoolId;
    private String grade;
    private String classNum;
//    private String schoolCode;
}
