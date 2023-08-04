package com.green.secondproject.teacher.model;

import lombok.Data;

@Data
public class SelMockResultVo {
    private Long resultId;
    private String year;
    private int mon;
    private String categoryId;
    private String nm;
    private int sc;
    private int rating;
    private int percent;
}
