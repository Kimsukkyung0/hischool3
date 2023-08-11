package com.green.secondproject.mypage.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdInfoParam {
    private String phone;
    private String address;
    private String detailAddr;
    private String pw;
}
