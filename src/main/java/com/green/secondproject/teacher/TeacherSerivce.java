package com.green.secondproject.teacher;

import com.green.secondproject.teacher.model.SelSignedStudentDto;
import com.green.secondproject.teacher.model.SelSignedStudentVo;
import com.green.secondproject.teacher.model.SelUnsignedStudentDto;
import com.green.secondproject.teacher.model.SelUnsignedStudentVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherSerivce {
    private final TeacherMapper mapper;


    public List<SelSignedStudentVo> selSignedStudent(int classId) {
        SelSignedStudentDto dto = new SelSignedStudentDto();
        dto.setClassId(classId);
        return mapper.selSignedStudent(dto);
    }


    public List<SelUnsignedStudentVo> selUnsignedStudent(int classId) {
        SelUnsignedStudentDto dto = new SelUnsignedStudentDto();
        dto.setClassId(classId);
        return mapper.selUnsignedStudent(dto);
    }
}
