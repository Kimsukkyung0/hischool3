package com.green.secondproject.teacher.model;

import lombok.Data;

@Data
public class UpdAcaResultDto {
    private Long resultId;
    private Long subjectId;
    private String year;
    private int semester;
    private int mf;
    private int score;
    private int rating;
    private int classRank;
    private int wholeRank;
}
