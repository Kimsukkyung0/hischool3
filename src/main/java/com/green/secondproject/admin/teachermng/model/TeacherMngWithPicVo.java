package com.green.secondproject.admin.teachermng.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class TeacherMngWithPicVo extends TeacherMngVo{
    private String aprPic;
}
