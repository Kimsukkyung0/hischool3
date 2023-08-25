package com.green.secondproject.header.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SelSchoolInfoVo {
    private Long userId;
    private Long schoolId;
    private String nm;
    private String grade;
    private String van;
}
