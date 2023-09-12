package com.green.secondproject.result.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalcClassRankParam {
    @Schema(hidden = true)
    private Long vanId;
    @Schema(hidden = true)
    private String year;
    @Schema(example = "1")
    private int semester;
    @Schema(example = "1")
    private int midFinal;
}
