package com.green.secondproject.common.config.security.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserEntity {
    private Long userId;
    private Long classId;
    private String email;
    private String pw;
    private String nm;
    private String pic;
    private LocalDate birth;
    private String phone;
    private String address;
    private String detailAddress;
    private String role;
    private String aprPic;
    private int aprYn;
}
