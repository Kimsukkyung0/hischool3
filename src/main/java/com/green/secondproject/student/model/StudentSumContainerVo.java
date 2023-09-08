package com.green.secondproject.student.model;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StudentSumContainerVo {
    private String date;
    private List<StudentSummarySubjectVo> list;

    @QueryProjection
    public StudentSumContainerVo(String date, List<StudentSummarySubjectVo> list) {
        this.date = date;
        this.list = list;
    }

}
