package com.green.secondproject.common.config.etc;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum Grade {
        FY(1, "1학년"),
        SY(2, "2학년"),
        TY(3, "3학년");

        private final int code;
        private final String displayName;
    }

