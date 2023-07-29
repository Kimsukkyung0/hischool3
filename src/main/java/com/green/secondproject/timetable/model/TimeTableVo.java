package com.green.secondproject.timetable.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class TimeTableVo {


    private String date;
    private int dayMonToSun;
    private String period;
    private String subject;



//    @JsonProperty("date")
//    public String getDate() {
//        return date;
//    }
//
//    @JsonProperty("ALL_TI_YMD")
//    public void setDate(String date) {
//        this.date = date;
//    }

//    public int getDayMonToSun(String date) {
////        StringBuffer sb = new StringBuffer(date);
////        sb.insert(4,'-');
////        sb.insert(7,'-');
////
////        LocalDate formLocalDate = LocalDate.parse(sb);
////
////        //요일값 넘기기
////        int dayMonToSun = formLocalDate.getDayOfWeek().getValue();
//
//        return dayMonToSun;
//    }
//
//    public void setDayMonToSun(int dayMonToSun) {
//        this.dayMonToSun = dayMonToSun;
//    }
//
    @JsonProperty("date")
    public String getDate() { return date;}

    @JsonProperty("ALL_TI_YMD")
    public void setDate(String date) { this.date = date; }

    @JsonProperty("period")
    public String getPerio() {
        return period;
    }
    @JsonProperty("PERIO")
    public void setPerio(String period) {
        this.period = period;
    }
    @JsonProperty("class_contents")
    public String getSubject() {
        return subject;
    }
    @JsonProperty("ITRT_CNTNT")
    public void setSubject(String subject) {
        this.subject = subject;
    }
}