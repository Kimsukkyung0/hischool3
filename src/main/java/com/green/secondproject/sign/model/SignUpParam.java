package com.green.secondproject.sign.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SignUpParam {
    private String email;
    private String pw;
    private String nm;
    private String schoolNm;
    private String grade;
    private String classNum;
    private LocalDate birth;
    private String phone;
    private String address;
    private String detailAddress;
    private String role;
}
