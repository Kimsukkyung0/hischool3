package com.green.secondproject.sign.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserVo {
    private Long userId;
    private String email;
    private String pw;
    private String nm;
    private Long classId;
    private String schoolNm;
    private String grade;
    private String classNum;
    private String pic;
    private String role;
}
