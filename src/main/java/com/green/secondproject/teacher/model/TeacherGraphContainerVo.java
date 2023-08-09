package com.green.secondproject.teacher.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TeacherGraphContainerVo {
    private String date;
    private List<List<TeacherGraphVo>> list;
}
