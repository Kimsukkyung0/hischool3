package com.green.secondproject.teacher.subject.model;

import lombok.Data;

@Data
public class ResultMockVo {
    private Long categoryId;
    private Long subjectId;
    private int standardScore;
    private int rating;
    private int percent;
}
