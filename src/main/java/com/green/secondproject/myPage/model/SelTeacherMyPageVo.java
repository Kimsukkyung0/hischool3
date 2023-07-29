package com.green.secondproject.myPage.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SelTeacherMyPageVo {
    private Long userId;
    private String tnm;
    private String email;
    private String pic;
    private LocalDate birth;
    private String phone;
    private String Address;
    private Long classId;
    private String grade;
    private String van;
    private Long schoolId;
    private String snm;
}
