package com.green.secondproject.student;

import com.green.secondproject.student.model.StudentDelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentMapper mapper;

    public int delStudent(int userId) {
        StudentDelDto dto = new StudentDelDto();
        dto.setUserId(userId);
        return mapper.delStudent(dto);
    }
}
