package com.green.secondproject.teacher.model;

import lombok.Data;

@Data
public class UpdMockResultDto {
    private Long resultId;
    private Long subjectId;
    private String year;
    private String mon;
    private int standardScore;
    private int rating;
    private int percent;
}
