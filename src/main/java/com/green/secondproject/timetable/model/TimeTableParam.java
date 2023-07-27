package com.green.secondproject.timetable.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TimeTableParam {
    private String sdSchulCode;//학교고유코드
    private String ay;

    private String grade;//학년
    private String classNm;//학반,학급

}
