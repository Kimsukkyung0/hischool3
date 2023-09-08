package com.green.secondproject.common.repository;

import com.green.secondproject.teacher.model.StudentVo;

import java.util.List;

public interface UserRepositoryCustom {
    List<StudentVo> studentList(String name);
}
