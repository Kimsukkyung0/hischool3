package com.green.secondproject.student.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class StudentSummarySubjectDto {
    @Schema(hidden = true)
    private Long userId;
    @Schema(description = "연도")
    private String year;
    @Schema(description = "월")
    private String mon;
}
