package com.green.secondproject.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GradeManagerVo {
    private String grade;
    private String nm;
    private String van;
    private String extNum;
}
