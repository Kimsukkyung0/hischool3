package com.green.secondproject.admin.schoolsubject.model;

import com.green.secondproject.common.config.etc.Grade;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ScSbjInsDto2 extends ScSbjInsDto{
    private Grade grade;
}
