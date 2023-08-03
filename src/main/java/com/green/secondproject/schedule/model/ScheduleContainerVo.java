package com.green.secondproject.schedule.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class ScheduleContainerVo {
    private String year;
    private String schoolNm;

    private List<ScheduleInfoVo> infoList;


}
