package com.green.secondproject.teacher.subject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AcaCategoryVo {
    private Long categoryId;
    private String nm;
}
