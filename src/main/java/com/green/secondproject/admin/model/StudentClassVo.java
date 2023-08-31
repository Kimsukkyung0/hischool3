package com.green.secondproject.admin.model;

import com.green.secondproject.admin.schoolsubject.model.ScSbjListVo2;
import com.green.secondproject.common.config.etc.EnrollState;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class StudentClassVo {
    private long userId;
    private String nm;
    private String email;
    private String phone;
    private EnrollState enrollState;
    private String grade;
    private String classNum;
//    private List<StudentClassVo> list;
//    private int totalCount;
//    private int totalPage;
}
