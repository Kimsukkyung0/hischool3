package com.green.secondproject.sign.model;

import com.green.secondproject.common.entity.SchoolEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassDto {
    private SchoolEntity schoolEntity;
    private String year;
    private String grade;
    private String classNum;
}
