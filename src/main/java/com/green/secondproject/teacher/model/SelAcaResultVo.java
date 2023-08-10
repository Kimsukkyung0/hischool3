package com.green.secondproject.teacher.model;

import lombok.Data;

@Data
public class SelAcaResultVo {
    private Long resultId;
    private String year;
    private Integer semester;
    private String cateId;
    private String nm;
    private String detailCateId;
    private String detailNm;
    private int mf;
    private int score;
    private int rating;
    private int cr;
    private int wr;
}
