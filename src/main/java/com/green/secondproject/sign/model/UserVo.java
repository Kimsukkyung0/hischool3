package com.green.secondproject.sign.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserVo {
    private Long userId;
    private String email;
    private String pw;
    private String role;
}
