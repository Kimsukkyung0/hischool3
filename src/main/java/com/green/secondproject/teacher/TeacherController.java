package com.green.secondproject.teacher;

import com.green.secondproject.teacher.model.SelSignedStudentVo;
import com.green.secondproject.teacher.model.SelUnsignedStudentVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@Tag(name = "선생님")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherSerivce service;

    @GetMapping
    @Operation(summary = "승인된 학생 리스트")
    public List<SelSignedStudentVo> SelectSignedStudent(@RequestParam int classId) {
        return service.selSignedStudent(classId);
    }


    @GetMapping("/unsigned")
    @Operation(summary = "승인 대기 명단")
    public List<SelUnsignedStudentVo> SelectUnsignedStudent(@RequestParam int classId) {
        return service.selUnsignedStudent(classId);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "선생님 탈퇴 처리")
    public int deleteTeacher(@RequestParam int userId) {
        return service.delTeacher(userId);
    }
}
