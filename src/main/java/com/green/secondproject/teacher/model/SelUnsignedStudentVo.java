package com.green.secondproject.teacher.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SelUnsignedStudentVo {
    private int classId;
    private int aprYn;
    private String unm;
    private LocalDate birth;
    private String phone;
    private String email;
}
