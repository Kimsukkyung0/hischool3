package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.AcaResultEntity;
import com.green.secondproject.common.entity.QAcaResultEntity;
import com.green.secondproject.student.model.StudentAcaResultsParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AcaResultRepositoryImpl implements AcaResultRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QAcaResultEntity acaResult = QAcaResultEntity.acaResultEntity;

    @Override
    public List<AcaResultEntity> searchAcaResult(StudentAcaResultsParam param) {
        return jpaQueryFactory
                .selectFrom(acaResult)
                .where(yearEq(param.getYear()), semesterEq(param.getSemester()), midFinalEq(param.getMidFinal()))
                .fetch();
    }

    private BooleanExpression yearEq(String year) {
        return year != null ? acaResult.year.eq(year) : null;
    }

    private BooleanExpression semesterEq(Integer semester) {
        return semester != null ? acaResult.semester.eq(semester) : null;
    }

    private BooleanExpression midFinalEq(Integer midFinal) {
        return midFinal != null ? acaResult.midFinal.eq(midFinal) : null;
    }
}
