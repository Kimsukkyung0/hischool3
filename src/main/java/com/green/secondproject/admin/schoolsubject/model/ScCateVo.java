package com.green.secondproject.admin.schoolsubject.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScCateVo {
    private Long categoryId;
    private String nm;
}
