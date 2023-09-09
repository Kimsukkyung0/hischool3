package com.green.secondproject.teacher.model;

import com.querydsl.core.annotations.QueryProjection;
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


    @QueryProjection
    public TeacherGraphVo(String cateNm, int rating, double percentage) {
        this.cateNm = cateNm;
        this.rating = rating;
        this.percentage = percentage;
    }
}
