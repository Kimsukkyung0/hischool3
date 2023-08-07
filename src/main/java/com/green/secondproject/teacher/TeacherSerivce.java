package com.green.secondproject.teacher;

import com.green.secondproject.teacher.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherSerivce {
    private final TeacherMapper mapper;


    public List<SelSignedStudentVo> selSignedStudent(Long classId) {
        SelSignedStudentDto dto = new SelSignedStudentDto();
        dto.setClassId(classId);
        return mapper.selSignedStudent(dto);
    }


    public List<SelUnsignedStudentVo> selUnsignedStudent(Long classId) {
        SelUnsignedStudentDto dto = new SelUnsignedStudentDto();
        dto.setClassId(classId);
        return mapper.selUnsignedStudent(dto);
    }

    public List<SelAcaResultVo> selAcaResult(Long userId) {
        SelAcaResultDto dto = new SelAcaResultDto();
        dto.setUserId(userId);
        return mapper.selAcaResult(dto);
    }

    public List<SelMockResultVo> selMockResult(Long userId) {
        SelMockResultDto dto = new SelMockResultDto();
        dto.setUserId(userId);
        return mapper.selMockResult(dto);
    }

    public int delTeacher(Long userId) {
        TeacherDelDto dto = new TeacherDelDto();
        dto.setUserId(userId);
        return mapper.delTeacher(dto);
    }
    public int classStudent(ClassStudentDto dto){
        return mapper.classStudent(dto);
    }
    public int aprStudent(Long classid){
        ClassStudentDto dto = new ClassStudentDto();
        dto.setClassid(classid);
        return mapper.aprStudent(dto);
    }

    public List<TeacherGrapeVo> teacherGrap(TeacherVanGrapeDto dto){

        return mapper.teacherGrap(dto);
    }
}

