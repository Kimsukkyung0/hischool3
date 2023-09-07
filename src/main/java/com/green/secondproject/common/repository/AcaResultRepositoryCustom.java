package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.student.model.StudentAcaResultWithIdVo;
import com.green.secondproject.student.model.StudentAcaResultsParam;
import com.green.secondproject.student.model.StudentTestSumGraphVo;

import java.util.List;

public interface AcaResultRepositoryCustom {
    List<StudentAcaResultWithIdVo> searchAcaResult(StudentAcaResultsParam param);


    List<StudentTestSumGraphVo> findAllByUserEntity(UserEntity userEntity);
}
