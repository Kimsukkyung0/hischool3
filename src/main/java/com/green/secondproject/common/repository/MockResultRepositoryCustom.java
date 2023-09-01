package com.green.secondproject.common.repository;

import com.green.secondproject.student.model.StudentMockSumResultWithIdVo;
import com.green.secondproject.student.model.StudentSummarySubjectDto;

import java.util.List;

public interface MockResultRepositoryCustom {
    List<StudentMockSumResultWithIdVo> searchMockResult(StudentSummarySubjectDto dto);
}
