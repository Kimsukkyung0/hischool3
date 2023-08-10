package com.green.secondproject.teacher.model;

import lombok.Data;

@Data
public class SelMockResultVo {
    private Long resultId;
    private String year;
    private Integer mon;
    private String cateId;
    private String nm;
    private String detailCateId;
    private String detailNm;
    private int sc;
    private int rating;
    private int percent;
}
