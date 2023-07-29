package com.green.secondproject.teacher;

import com.green.secondproject.teacher.model.*;
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

    public List<SelAcaResultVo> selAcaResult(int userId) {
        SelAcaResultDto dto = new SelAcaResultDto();
        dto.setUserId(userId);
        return mapper.selAcaResult(dto);
    }

    public List<SelMockResultVo> selMockResult(int userId) {
        SelMockResultDto dto = new SelMockResultDto();
        dto.setUserId(userId);
        return mapper.selMockResult(dto);
    }

    public int delTeacher(int userId) {
        TeacherDelDto dto = new TeacherDelDto();
        dto.setUserId(userId);
        return mapper.delTeacher(dto);
    }
}

