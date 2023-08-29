package com.green.secondproject.career.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CareerInsDto {
    private Long userId; // 이건 작성하는 사람 값자동입력?
    private String grade;
    private String interest;
    private String stdHope;
    private String parentHope;
    private String hopeUniv;
    private String hopeDept;
    private String specialNote;
}
