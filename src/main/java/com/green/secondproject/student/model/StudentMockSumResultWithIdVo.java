package com.green.secondproject.student.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentMockSumResultWithIdVo {
    private Long resultId;
    private String year;
    private String mon;
    private String cateName;
    private String nm;
    private int standardScore;
    private int rating;
    private int percent;

    @QueryProjection
    public StudentMockSumResultWithIdVo(Long resultId, String year, String mon, String cateName, String nm, int standardScore, int rating, int percent) {
        this.resultId = resultId;
        this.year = year;
        this.mon = mon;
        this.cateName = cateName;
        this.nm = nm;
        this.standardScore = standardScore;
        this.rating = rating;
        this.percent = percent;
    }
}
