package com.green.secondproject.teacher.subject.model;

import lombok.Data;

@Data
public class AcaSubjectDto {
    private Long user_id;
    private Long student_id;
    private Long subject_id;
    private int year;
    private int semeter;
    private int mid_final;
    private int score;
    private int rating;
    private int class_rank;
    private int whole_rank;
}
