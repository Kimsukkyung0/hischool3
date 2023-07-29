package com.green.secondproject.teacher.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SelSignedStudentVo {
    private int classId;
    private int aprYn;
    private String snm;
    private LocalDate birth;
    private String phone;
    private String email;
}
