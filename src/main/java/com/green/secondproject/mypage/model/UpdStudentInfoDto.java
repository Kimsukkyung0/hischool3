package com.green.secondproject.mypage.model;

import lombok.Data;

@Data
public class UpdStudentInfoDto {
    private Long userId;
    private String nm;
    private String phone;
    private String address;
    private String pw;
    private String verpw;
}
