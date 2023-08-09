package com.green.secondproject.teacher.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TeacherGraphVo {
    private String date;
    private int categoryId;
    private String subjectNm;
    private int rating;
}
