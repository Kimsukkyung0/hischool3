package com.green.secondproject.result.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MockResultDto {
    @Schema(example = "2023")
    private String year;
    @Schema(example = "9")
    private String mon;
    @Schema(example = "국어")
    private String cateName;
    @Schema(example = "국어")
    private String nm;
    @Schema(example = "123")
    private int standardScore;
    @Schema(example = "3")
    private int rating;
    @Schema(example = "25")
    private int percent;
}
