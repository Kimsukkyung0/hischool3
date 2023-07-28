package com.green.secondproject.student;

import com.green.secondproject.student.model.StudentDelDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {
    int delStudent(StudentDelDto dto);
}
