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
    int acceptStudent(AcceptStudentDto dto);
    int rejectStudent(AcceptStudentDto dto);
    int delTeacher(TeacherDelDto dto);
    int delMockResult(DelResultDto dto);
    int delAcaResult(DelResultDto dto);
    int classStudent(ClassStudentDto dto);
    int aprStudent(ClassStudentDto dto);
    List<TeacherGrapeVo> teacherGrap(TeacherVanGrapeDto dto);
}
