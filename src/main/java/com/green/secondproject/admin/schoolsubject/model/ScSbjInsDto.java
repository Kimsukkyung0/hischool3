package com.green.secondproject.admin.schoolsubject.model;

import com.green.secondproject.common.config.etc.Grade;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ScSbjInsDto {
    private Long subjectId;
    private Long schoolId;
}
