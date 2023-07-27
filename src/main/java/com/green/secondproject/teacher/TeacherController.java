package com.green.secondproject.teacher;

import com.green.secondproject.teacher.model.SelSignedStudentVo;
import com.green.secondproject.teacher.model.SelUnsignedStudentVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@Tag(name = "선생님")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherSerivce service;

    @GetMapping
    public List<SelSignedStudentVo> SelectSignedStudent(@RequestParam int classId) {
        return service.selSignedStudent(classId);
    }


    @GetMapping("/unsigned")
    public List<SelUnsignedStudentVo> SelectUnsignedStudent(@RequestParam int classId) {
        return service.selUnsignedStudent(classId);
    }
}
