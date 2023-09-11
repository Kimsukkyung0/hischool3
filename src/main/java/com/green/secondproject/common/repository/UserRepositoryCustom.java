package com.green.secondproject.common.repository;

import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.VanEntity;
import com.green.secondproject.teacher.model.StudentVo;

import java.util.List;

public interface UserRepositoryCustom {
    List<StudentVo> studentList(String name, Long vanId);

}
