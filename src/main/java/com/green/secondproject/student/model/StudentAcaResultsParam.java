package com.green.secondproject.student.model;

import lombok.Data;

@Data
public class StudentAcaResultsParam {
    private String year;
    private int semester;
    private int midFinal;
    private Long userId;

}
