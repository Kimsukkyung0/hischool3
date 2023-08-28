package com.green.secondproject.admin.subject.model;

import lombok.Data;

@Data
public class ResultMockVo {
    private Long categoryId;
    private Long subjectId;
    private int standardScore;
    private int rating;
    private int percent;
    private int mon;
}
