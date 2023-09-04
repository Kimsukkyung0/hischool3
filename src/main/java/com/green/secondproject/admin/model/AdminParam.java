package com.green.secondproject.admin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AdminParam {
    @Schema(example = "admin1@naver.com", description = "이메일")
    private String email;
    @Schema(example = "gkrtod123!", description = "비밀번호")
    private String pw;
    @Schema(example = "7240103", description = "학교코드")
    private String schoolCode;
    @Schema(example = "청구고등학교", description = "학교명")
    private String schoolNm;
}
