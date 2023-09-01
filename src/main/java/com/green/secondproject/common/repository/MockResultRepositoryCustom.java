package com.green.secondproject.common.repository;

import com.green.secondproject.student.model.StudentMockSumResultWithIdVo;
import com.green.secondproject.student.model.StudentSummarySubjectDto;
import com.green.secondproject.student.model.StudentSummarySubjectVo;

import java.util.List;

public interface MockResultRepositoryCustom {
    List<StudentMockSumResultWithIdVo> searchMockResult(StudentSummarySubjectDto dto);
    List<StudentSummarySubjectVo> getHighestRatingsOfMockTest(Long userId);
}
