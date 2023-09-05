package com.green.secondproject.acaResult.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
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
