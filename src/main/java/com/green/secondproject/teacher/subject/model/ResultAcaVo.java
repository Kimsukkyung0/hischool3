package com.green.secondproject.teacher.subject.model;

import lombok.Data;

@Data
public class ResultAcaVo {
    private Long categoryId;
    private Long subjectId;
    private int score;
    private int rating;
    private int classRank;
    private int wholeRank;
}
