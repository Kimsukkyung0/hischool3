package com.green.secondproject.sign.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SignUpParam {
    @Schema(example = "std1@naver.com", description = "이메일")
    private String email;
    @Schema(example = "gkrtod123!", description = "비밀번호")
    private String pw;
    @Schema(example = "김학생", description = "이름")
    private String nm;
    @Schema(example = "7240103", description = "학교코드")
    private String schoolCode;
    @Schema(example = "청구고등학교", description = "학교명")
    private String schoolNm;
    @Schema(example = "1", description = "학년")
    private String grade;
    @Schema(example = "1", description = "반")
    private String classNum;
    private LocalDate birth;
    @Schema(example = "010-1234-5678", description = "휴대폰 번호")
    private String phone;
    @Schema(example = "대구광역시 중구 중앙대로 394", description = "주소")
    private String address;
    @Schema(example = "제일빌딩 5F", description = "상세주소")
    private String detailAddress;
    @Schema(example = "STD", description = "권한")
    private String role;
}
