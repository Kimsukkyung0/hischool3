package com.green.secondproject.admin.schoolsubject.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScSbjGradeVo {
    private Long subjectId;
    private String subjectNm;
    private Long scSbjId;
}
