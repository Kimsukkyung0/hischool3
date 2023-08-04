package com.green.secondproject.sign.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class SignInResultDto extends SignUpResultDto {
    private String accessToken;
    private String refreshToken;
    private String role;
}
