package com.green.secondproject.admin.teachermng.model;

import com.green.secondproject.common.config.etc.EnrollState;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Data
public class TeacherMngVo {
    private Long userId;
    private String schoolNm;
    private String grade;
    private String vanNum;
    private String email;
    private String nm;
    private LocalDate birth;
    private String phone;
    private String address;
    private String detailAddr;
    private String role;
    private int aprYn;
    private EnrollState enrollState;
}
