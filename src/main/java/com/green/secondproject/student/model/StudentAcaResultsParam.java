package com.green.secondproject.student.model;

import lombok.Data;

@Data
public class StudentAcaResultsParam {
    private String year;
    private Integer semester;
    private Integer midFinal;
    private Long userId;
}
