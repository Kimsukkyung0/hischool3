package com.green.secondproject.student.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentMockSumResultVo {
    private String year;
    private String mon;
    private String cateName;
    private String nm;
    private String standardScore;
    private String rating;
    private String percent;
}
