package com.green.secondproject;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonRes {
    SUCCESS(0, "Success"), FAIL(-1, "Fail");

    int code;
    String msg;
}
