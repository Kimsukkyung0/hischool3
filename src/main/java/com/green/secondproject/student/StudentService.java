package com.green.secondproject.student;

import com.green.secondproject.student.model.StudentDelDto;
import com.green.secondproject.student.model.StudentMockResultVo;
import com.green.secondproject.student.model.StudentMockResultsParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentMapper mapper;

    public int delStudent(int userId) {
        StudentDelDto dto = new StudentDelDto();
        dto.setUserId(userId);
        return mapper.delStudent(dto);
    }

    public List<StudentMockResultVo> selMockTestResultByDates(StudentMockResultsParam param){
        return mapper.selMockTestResultByDates(param);
    }
}
