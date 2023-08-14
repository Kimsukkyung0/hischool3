package com.green.secondproject.teacher.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class TeacherGraphVo {
    private String cateNm;
    private int rating;
    private double percentage;
}
