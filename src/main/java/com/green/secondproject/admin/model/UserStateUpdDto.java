package com.green.secondproject.admin.model;

import com.green.secondproject.common.entity.VanEntity;
import lombok.Data;

@Data
public class UserStateUpdDto {
    private long userId;
//    private String grade;
//    private String classNum;
    private Long vanId;
}
