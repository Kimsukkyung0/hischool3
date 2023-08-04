package com.green.secondproject.student.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class StudentSummaryContainerVo {
    private String date;
    private List<StudentSummarySubjectVo> list;
    public StudentSummaryContainerVo(String date, List<StudentSummarySubjectVo> list) {
        this.date = date;
        this.list = list;
    }


}
