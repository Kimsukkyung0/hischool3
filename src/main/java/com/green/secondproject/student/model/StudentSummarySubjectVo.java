package com.green.secondproject.student.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class StudentSummarySubjectVo {
    private String nm;
    private int rating;

    @QueryProjection
    public StudentSummarySubjectVo(String nm, int rating) {
        this.nm = nm;
        this.rating = rating;
    }
}

