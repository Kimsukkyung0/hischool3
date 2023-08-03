package com.green.secondproject.teacher.model;

import lombok.Data;

@Data
public class SelAcaResultVo {
    private Long resultId;
    private char year;
    private int semester;
    private String categoryId;
    private String nm;
    private int mf;
    private int score;
    private int rating;
    private int cr;
    private int wr;
}
