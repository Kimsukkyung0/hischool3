package com.green.secondproject.admin.schoolsubject.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScSbjVo {
        private Long subjectId;
        private String subjectNm;
}
