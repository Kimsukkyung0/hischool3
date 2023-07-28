package com.green.secondproject.sign.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassDto {
    private int year;
    private int grade;
    private int classNum;
}
