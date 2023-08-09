package com.green.secondproject.mypage.model;

import lombok.Data;

@Data
public class UpdUserInfoDto {
    private String nm;
    private String phone;
    private String address;
    private String detailAddr;
    private String pw;
    private String confirmPw;
}
