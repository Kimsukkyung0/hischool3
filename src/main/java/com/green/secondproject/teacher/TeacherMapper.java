package com.green.secondproject.teacher;

import com.green.secondproject.teacher.model.SelSignedStudentDto;
import com.green.secondproject.teacher.model.SelSignedStudentVo;
import com.green.secondproject.teacher.model.SelUnsignedStudentDto;
import com.green.secondproject.teacher.model.SelUnsignedStudentVo;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherMapper {
    List<SelSignedStudentVo> selSignedStudent(SelSignedStudentDto dto);
    List<SelUnsignedStudentVo> selUnsignedStudent(SelUnsignedStudentDto dto);
}
