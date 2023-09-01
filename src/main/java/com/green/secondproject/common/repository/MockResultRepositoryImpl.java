package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.QMockResultEntity;
import com.green.secondproject.student.model.QStudentMockSumResultWithIdVo;
import com.green.secondproject.student.model.StudentMockSumResultWithIdVo;
import com.green.secondproject.student.model.StudentSummarySubjectDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MockResultRepositoryImpl implements MockResultRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QMockResultEntity mockResult = QMockResultEntity.mockResultEntity;

    @Override
    public List<StudentMockSumResultWithIdVo> searchMockResult(StudentSummarySubjectDto dto) {
        return jpaQueryFactory
                .select(new QStudentMockSumResultWithIdVo(mockResult.resultId, mockResult.year, mockResult.mon,
                        mockResult.subjectEntity.sbjCategoryEntity.nm, mockResult.subjectEntity.nm,
                        mockResult.standardScore, mockResult.rating, mockResult.percent))
                .from(mockResult)
                .where(mockResult.userEntity.userId.eq(dto.getUserId()),
                        yearEq(dto.getYear()), monEq(dto.getMon()))
                .orderBy(mockResult.year.desc(), mockResult.mon.desc(),
                        mockResult.subjectEntity.sbjCategoryEntity.nm.asc())
                .fetch();
    }

    private BooleanExpression yearEq(String year) {
        return year != null ? mockResult.year.eq(year) : null;
    }

    private BooleanExpression monEq(String mon) {
        return mon != null ? mockResult.mon.eq(mon) : null;
    }
}
