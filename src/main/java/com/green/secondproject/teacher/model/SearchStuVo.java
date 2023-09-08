package com.green.secondproject.teacher.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchStuVo {
    private Long userId;
    private String nm;


}
