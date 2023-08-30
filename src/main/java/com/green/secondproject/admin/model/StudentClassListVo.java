package com.green.secondproject.admin.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StudentClassListVo {
    private List<StudentClassVo> list1;
    private List<StudentClassVo> list2;
}
