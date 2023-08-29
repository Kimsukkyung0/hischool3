package com.green.secondproject.admin.schoolsubject.model;

import com.green.secondproject.common.config.etc.Grade;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScSbjVo {
    private Long scSbjId;
    private Grade grade;
    private Long subjectId;
}
