package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.student.model.StudentMockSumResultWithIdVo;
import com.green.secondproject.student.model.StudentSummarySubjectDto;
import com.green.secondproject.student.model.StudentSummarySubjectVo;
import com.green.secondproject.student.model.StudentTestSumGraphVo;

import java.util.List;

public interface MockResultRepositoryCustom {
    List<StudentMockSumResultWithIdVo> searchMockResult(StudentSummarySubjectDto dto);
    List<StudentSummarySubjectVo> getHighestRatingsOfMockTest(Long userId);

    List<StudentTestSumGraphVo> getLatestRatingsOfMockTest(UserEntity userEntity);
    List<StudentTestSumGraphVo> getMockTestGraph(UserEntity userEntity);
}
