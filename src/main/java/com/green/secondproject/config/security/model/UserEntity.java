package com.green.secondproject.config.security.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class UserEntity {
    private Long userId;
    private String email;
    private String pw;
    private String nm;
    private String pic;
    private LocalDate birth;
    private String phone;
    private String address;
    private String role;
}
