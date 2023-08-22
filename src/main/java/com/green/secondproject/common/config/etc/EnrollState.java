package com.green.secondproject.common.config.etc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnrollState {
    ENROLL("재학"),
    GRADUATION("졸업"),
    TRANSFER("전학"),
    LEAVE("자퇴");

    private final String displayName;
}
