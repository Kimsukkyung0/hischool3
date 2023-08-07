package com.green.secondproject.mypage.model;

import lombok.Builder;
import lombok.Data;

@Data
public class UpdPicDto {
    private Long userId;
    private String pic;
}
