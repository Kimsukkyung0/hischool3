package com.green.secondproject.student;

import com.green.secondproject.student.model.*;
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
    public List<StudentAcaResultVo> selAcaTestResultByDatesAndPeriod(StudentAcaResultsParam param){
        return mapper.selAcaTestResultByDatesAndPeriod(param);
    };

    public StudentSummarySubjectVo getHighestRatingsOfMockTest(StudentSummaryParam param){
        return mapper.getHighestRatingsOfMockTest(param);
    }

    public StudentSummarySubjectVo getLatestRatingsOfMockTest(StudentSummaryParam param){
        return mapper.getLatestRatingsOfMockTest(param);
    }
}
