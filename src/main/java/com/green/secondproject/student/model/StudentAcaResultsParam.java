package com.green.secondproject.student.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentAcaResultsParam {
    @Schema(description = "연도")
    private String year;
    @Schema(description = "학기")
    private Integer semester;
    @Schema(description = "중간(1), 기말(2)")
    private Integer midFinal;
    @Schema(hidden = true)
    private Long userId;
    @Schema(hidden = true)
    private Long vanId;
    @Schema(hidden = true)
    private String grade;
    @Schema(hidden = true)
    private Long schoolId;
}
