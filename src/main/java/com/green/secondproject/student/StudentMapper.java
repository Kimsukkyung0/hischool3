package com.green.secondproject.student;

import com.green.secondproject.student.model.StudentDelDto;
import com.green.secondproject.student.model.StudentMockResultVo;
import com.green.secondproject.student.model.StudentMockResultsParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    int delStudent(StudentDelDto dto);
    List<StudentMockResultVo> selMockTestResultByDates(StudentMockResultsParam param);
}
