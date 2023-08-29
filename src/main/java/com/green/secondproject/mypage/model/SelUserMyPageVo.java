package com.green.secondproject.mypage.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SelUserMyPageVo {
    private Long userId;
    private String unm;
    private String email;
    private String role;
    private String pic;
    private LocalDate birth;
    private String phone;
    private String address;
    private String detailAddr;
    private Long classId;
    private String grade;
    private String van;
    private Long schoolId;
    private String schnm;
}
