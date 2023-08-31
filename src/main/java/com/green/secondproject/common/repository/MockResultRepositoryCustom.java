package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.AcaResultEntity;
import com.green.secondproject.common.entity.MockResultEntity;
import com.green.secondproject.student.model.StudentAcaResultsParam;
import com.green.secondproject.student.model.StudentSummarySubjectDto;

import java.util.List;

public interface MockResultRepositoryCustom {
    List<MockResultEntity> searchMockResult(StudentSummarySubjectDto dto);
}
