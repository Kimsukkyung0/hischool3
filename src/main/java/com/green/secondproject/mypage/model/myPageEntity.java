package com.green.secondproject.mypage.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class myPageEntity {
    private Long userId;
    private String pic;
    private String nm;
    private String phone;
    private String address;
    private String detailAddr;
    private String pw;
    private String confirmPw;
}

