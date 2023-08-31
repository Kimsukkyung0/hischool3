package com.green.secondproject.admin.model;

import com.green.secondproject.common.config.etc.EnrollState;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StudentClassVo2 {
    private List<StudentClassVo> list;
    private int totalCount;
    private int totalPage;
}
