package com.green.secondproject.teacher.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class TeacherGraphVo2 {
    private String date;
    private String subjectNm;
    private String rating;
    private int percent;
}
