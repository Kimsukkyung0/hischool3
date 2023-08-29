package com.green.secondproject.career.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CareerUpDto {
    private Long careerId;
    private String grade;
    private String interest;
    private String stdHope;
    private String parentHope;
    private String hopeUniv;
    private String hopeDept;
    private String specialNote;
}
