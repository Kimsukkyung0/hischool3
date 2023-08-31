package com.green.secondproject.common.repository;

import com.green.secondproject.student.model.StudentAcaResultWithIdVo;
import com.green.secondproject.student.model.StudentAcaResultsParam;

import java.util.List;

public interface AcaResultRepositoryCustom {
    List<StudentAcaResultWithIdVo> searchAcaResult(StudentAcaResultsParam param);
}
