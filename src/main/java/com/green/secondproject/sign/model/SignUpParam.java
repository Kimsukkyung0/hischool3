package com.green.secondproject.sign.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpParam {
    private String email;
    private String pw;
    private String nm;
    private String schoolNm;
    private String grade;
    private String classNm;
    private LocalDate birth;
    private String phone;
    private String address;
    private String role;
}
