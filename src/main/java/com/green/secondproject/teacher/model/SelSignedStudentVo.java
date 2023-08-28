package com.green.secondproject.teacher.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SelSignedStudentVo {
    private Long userId;
    private Long classId;
    private int aprYn;
    private String snm;
    private LocalDate birth;
    private String phone;
    private String email;
}
