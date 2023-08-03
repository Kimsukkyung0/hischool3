package com.green.secondproject.teacher.subject.model;

import lombok.Data;

@Data
public class AcaSubjectVo {
    private Long userid;
    private Long subjectid;
    private int semester;
    private int midfinal;
    private int score;
    private int rating;
    private int classrank;
    private int wholerank;
}
