package com.green.secondproject.schedule.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ScheduleInfoVo {
    private String eventNm; //행사명
    private String aaYmd;

    @JsonProperty("eventNm")
    public String getEventNm() {
        return eventNm;
    }
    @JsonProperty("EVENT_NM")
    public void setEventNm(String eventNm) {
        this.eventNm = eventNm;
    }
    @JsonProperty("aaYmd")
    public String getAaYmd() {
        return aaYmd;
    }
    @JsonProperty("AA_YMD")
    public void setAaYmd(String aaYmd) {
        this.aaYmd = aaYmd;
    }
}
