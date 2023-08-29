package com.green.secondproject.career.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CareerVo {
    private Long userId;
    private Long careerId;
    private String grade;
    private String interest;
    private String stdHope;
    private String parentHope;
    private String hopeUniv;
    private String hopeDept;
    private String specialNote;
}
