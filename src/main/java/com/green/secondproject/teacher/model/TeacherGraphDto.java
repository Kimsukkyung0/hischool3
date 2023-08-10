package com.green.secondproject.teacher.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeacherGraphDto {
    private Long categoryId;
    private Long classId;
}
