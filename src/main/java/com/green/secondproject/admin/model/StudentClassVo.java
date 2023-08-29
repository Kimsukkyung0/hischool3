package com.green.secondproject.admin.model;

import com.green.secondproject.common.config.etc.EnrollState;
import lombok.Builder;
import lombok.Getter;

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
}
