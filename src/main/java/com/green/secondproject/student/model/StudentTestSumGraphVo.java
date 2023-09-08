package com.green.secondproject.student.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentTestSumGraphVo {
    private String date;
    private String nm;
    private int rating;

    @QueryProjection
    public StudentTestSumGraphVo(String date, String nm, int rating) {
        this.date = date;
        this.nm = nm;
        this.rating = rating;
    }



}

