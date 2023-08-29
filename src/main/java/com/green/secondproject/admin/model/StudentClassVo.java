package com.green.secondproject.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentClassVo {
    private String nm;
    private String grade;
    private String classNum;
}
