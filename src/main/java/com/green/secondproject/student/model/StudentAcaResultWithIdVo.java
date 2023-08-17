package com.green.secondproject.student.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentAcaResultWithIdVo {
    private Long resultId;
    private String year;
    private int semester;
    private int midFinal;
    private String cateName;
    private String nm;
    private int score;
    private int rating;
    private int classRank;
    private int wholeRank;

}
