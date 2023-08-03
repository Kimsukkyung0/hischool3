package com.green.secondproject.schedule.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.web.PortResolverImpl;

@Data
@ToString
public class ScheduleParam {
    private String sdSchulCode; //학교코드
    private String aaFromYmd;
    private String aaToYmd;
}