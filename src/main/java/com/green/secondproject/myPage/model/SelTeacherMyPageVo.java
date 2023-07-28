package com.green.secondproject.myPage.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SelTeacherMyPageVo {
    private int userId;
    private String tnm;
    private String email;
    private String pic;
    private LocalDate birth;
    private String phone;
    private String Address;
    private int classId;
    private int grade;
    private int van;
    private int schoolId;
    private String snm;
}
