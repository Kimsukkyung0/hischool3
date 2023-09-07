package com.green.secondproject.student.model;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringExpression;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentTestSumGraphVo {
    private String date;
    private String nm;
    private NumberExpression<Integer> rating;

    @QueryProjection
    public StudentTestSumGraphVo(StringExpression date, StringExpression nm, NumberExpression<Integer> rating) {
        this.date = date.toString();
        this.nm = nm.toString();
        this.rating = rating.intValue();
    }
}

