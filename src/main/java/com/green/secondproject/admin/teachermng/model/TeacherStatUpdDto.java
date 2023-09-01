package com.green.secondproject.admin.teachermng.model;

import com.green.secondproject.common.config.etc.EnrollState;
import lombok.Data;

@Data
public class TeacherStatUpdDto {
    private Long userId;
    private EnrollState enrollState;
    private int classNum;
    private int grade;

}
