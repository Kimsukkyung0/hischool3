package com.green.secondproject.timetable.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TimeTableVo {
    private String period;
    private String subject;



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