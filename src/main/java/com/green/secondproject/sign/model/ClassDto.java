package com.green.secondproject.sign.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassDto {
    private Long schoolId;
    private String year;
    private String grade;
    private String classNum;
}
