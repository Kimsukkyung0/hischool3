package com.green.secondproject.sign.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignInParam {
    @Schema(example = "admin1@naver.com", description = "이메일")
    private String email;
    @Schema(example = "gkrtod123!", description = "비밀번호")
    private String pw;
}
