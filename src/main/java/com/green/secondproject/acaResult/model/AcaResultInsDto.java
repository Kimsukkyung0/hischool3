package com.green.secondproject.acaResult.model;

import com.green.secondproject.teacher.subject.model.AcaResultDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class AcaResultInsDto {
    private Long userId;
    @Schema(example = "1")
    private int semester;
    @Schema(example = "1")
    private int midFinal;
    private List<AcaResultDto> list;
}
