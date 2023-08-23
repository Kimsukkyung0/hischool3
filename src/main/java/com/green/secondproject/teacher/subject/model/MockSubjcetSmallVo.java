package com.green.secondproject.teacher.subject.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MockSubjcetSmallVo {
    private Long subjectid;
    private String nm;
    private Long categoryid;
}
