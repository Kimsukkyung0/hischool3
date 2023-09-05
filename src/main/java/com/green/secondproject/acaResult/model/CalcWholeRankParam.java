package com.green.secondproject.acaResult.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CalcWholeRankParam {
    private int semester;
    private int midFinal;
    private String year;
    private Long schoolId;
    private String grade;
}
