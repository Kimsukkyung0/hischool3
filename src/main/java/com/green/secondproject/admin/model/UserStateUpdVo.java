package com.green.secondproject.admin.model;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class UserStateUpdVo {
//    private String grade;
//    private String classNum;
    private long userId;
    private long vanId;
    private String schoolNm;
    private String grade;
    private String classNum;
    private String nm;
}
