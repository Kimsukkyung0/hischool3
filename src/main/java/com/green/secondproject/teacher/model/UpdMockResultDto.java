package com.green.secondproject.teacher.model;

import lombok.Data;

@Data
public class UpdMockResultDto {
    private Long resultId;
    private String year;
    private int mon;
    private int standardScore;
    private int rating;
    private int percent;
}
