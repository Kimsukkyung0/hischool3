package com.green.secondproject.career.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CareerVo {
    private Long user_id;
    private String grade;
    private String interest;
    private String std_hope;
    private String parent_hope;
    private String hope_univ;
    private String hope_dept;
    private String special_note;
}
