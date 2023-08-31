package com.green.secondproject.student.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentMockSumResultWithIdVo {
    private Long resultId;
    private String year;
    private String mon;
    private String cateName;
    private String nm;
    private int standardScore;
    private int rating;
    private int percent;
}
