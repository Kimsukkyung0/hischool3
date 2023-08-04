package com.green.secondproject.student.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class StudentSummarySubjectVo {
    private String nm;
    private String rating;

    public StudentSummarySubjectVo(String nm, String rating) {
        this.nm = nm;
        this.rating = rating;
    }

}

