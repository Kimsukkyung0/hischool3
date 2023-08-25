package com.green.secondproject.admin.teachermng.model;

import com.green.secondproject.common.config.etc.EnrollState;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class TeacherMngVo {
    private Long userId;
    private Long classId;
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
