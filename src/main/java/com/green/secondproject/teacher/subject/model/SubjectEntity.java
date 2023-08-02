package com.green.secondproject.teacher.subject.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SubjectEntity {
    private Long subjectid;
    private String nm;
    private Long categoryid;

}
