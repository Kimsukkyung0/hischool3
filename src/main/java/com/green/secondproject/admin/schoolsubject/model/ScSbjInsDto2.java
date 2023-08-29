package com.green.secondproject.admin.schoolsubject.model;

import com.green.secondproject.common.config.etc.Grade;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class ScSbjInsDto2 extends ScSbjInsDto{
    private Grade grade;
}
