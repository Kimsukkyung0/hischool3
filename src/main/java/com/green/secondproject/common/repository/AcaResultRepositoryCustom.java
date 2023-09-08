package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.student.model.*;

import java.util.List;

public interface AcaResultRepositoryCustom {
    List<StudentAcaResultWithIdVo> searchAcaResult(StudentAcaResultsParam param);
    List<StudentTestSumGraphVo> getLatestRatingsOfAcaTest(UserEntity userEntity);
    List<StudentSummarySubjectVo> getHighestRatingOfAcaTest(UserEntity userEntity);
}
