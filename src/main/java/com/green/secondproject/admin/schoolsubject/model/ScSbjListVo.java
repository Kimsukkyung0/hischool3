package com.green.secondproject.admin.schoolsubject.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScSbjListVo {
    private Long scSbjId;
    private String grade;
    private Long subjectId;
}
