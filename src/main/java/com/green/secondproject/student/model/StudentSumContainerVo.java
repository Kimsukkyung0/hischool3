package com.green.secondproject.student.model;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StudentSumContainerVo {
    private String date;
    private List<StudentSummarySubjectVo> list;

    public StudentSumContainerVo(String date, List<StudentSummarySubjectVo> list) {
        this.date = date;
        this.list = list;
    }

}
