package com.green.secondproject.sign.model;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SchoolParam {
    @Parameter(description = "학교코드", example = "7240103", required = true)
    private String schoolCode;
    @Parameter(description = "학년", example = "1", required = true)
    private String grade;
}
