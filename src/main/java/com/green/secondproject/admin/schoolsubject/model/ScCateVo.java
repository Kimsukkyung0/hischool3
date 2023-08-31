package com.green.secondproject.admin.schoolsubject.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@Builder
public class ScCateVo {
    private Long categoryId;
    private String categoryNm;
}
