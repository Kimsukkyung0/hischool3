package com.green.secondproject.student.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentAcaResultsParam {
    private String year;
    private Integer semester;
    private Integer midFinal;
    private Long userId;
}
