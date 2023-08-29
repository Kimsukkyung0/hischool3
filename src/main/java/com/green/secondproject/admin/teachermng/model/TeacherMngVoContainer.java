package com.green.secondproject.admin.teachermng.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TeacherMngVoContainer {
    private List<TeacherMngVo> list;
    private int totalCount;
    private int totalPage;

}
