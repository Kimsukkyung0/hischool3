package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.AcaResultEntity;
import com.green.secondproject.student.model.StudentAcaResultsParam;

import java.util.List;

public interface AcaResultRepositoryCustom {
    List<AcaResultEntity> searchAcaResult(StudentAcaResultsParam param);
}
