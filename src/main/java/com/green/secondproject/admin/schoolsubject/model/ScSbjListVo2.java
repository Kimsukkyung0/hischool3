package com.green.secondproject.admin.schoolsubject.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScSbjListVo2 {
    private Long categoryId;
    private String categoryNm;
    private Long subjectId;
    private String subjectNm;
    private Long scSbjId;
}
