package com.green.secondproject.student;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
@Tag(name = "학생")
public class StudentController {
    private final StudentService service;


    @DeleteMapping("/delete")
    @Operation(summary = "학생 탈퇴 처리")
    public int deleteStudent(@RequestParam int userId) {
        return service.delStudent(userId);
    }
}
