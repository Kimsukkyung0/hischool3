package com.green.secondproject.teacher;

import com.green.secondproject.teacher.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherMapper {
    List<SelSignedStudentVo> selSignedStudent(SelSignedStudentDto dto);
    List<SelUnsignedStudentVo> selUnsignedStudent(SelUnsignedStudentDto dto);
    List<SelAcaResultVo> selAcaResult(SelAcaResultDto dto);
    List<SelMockResultVo> selMockResult(SelMockResultDto dto);
    int delTeacher(TeacherDelDto dto);
}
