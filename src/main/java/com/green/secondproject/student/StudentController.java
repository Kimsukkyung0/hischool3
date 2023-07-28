package com.green.secondproject.student;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService service;


    @DeleteMapping("/delete")
    public int deleteStudent(@RequestParam int userId) {
        return service.delStudent(userId);
    }
}
