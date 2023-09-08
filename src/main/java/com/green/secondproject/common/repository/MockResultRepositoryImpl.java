package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.*;
import com.green.secondproject.common.utils.MyGradeGraphUtils;
import com.green.secondproject.student.model.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MockResultRepositoryImpl implements MockResultRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QMockResultEntity mockResult = QMockResultEntity.mockResultEntity;
    private final QSbjCategoryEntity sbjCategory = QSbjCategoryEntity.sbjCategoryEntity;
    private final MyGradeGraphUtils myGrade;

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

    @Override
    public List<StudentSummarySubjectVo> getHighestRatingsOfMockTest(Long userId) {
        return jpaQueryFactory
                .select(new QStudentSummarySubjectVo(sbjCategory.nm,
                        mockResult.rating.min()))
                .from(mockResult)
                .innerJoin(mockResult.subjectEntity.sbjCategoryEntity, sbjCategory)
                .where(mockResult.userEntity.userId.eq(userId),
                        sbjCategory.categoryId.in(JPAExpressions
                                .select(sbjCategory.categoryId)
                                .from(sbjCategory)
                                .where(sbjCategory.nm.in(
                                                "국어", "수학", "영어", "한국사"),
                                        sbjCategory.type.eq(2))))
                .groupBy(sbjCategory.nm)
                .fetch();
    }

    private BooleanExpression yearEq(String year) {
        return year != null ? mockResult.year.eq(year) : null;
    }

    private BooleanExpression monEq(String mon) {
        return mon != null ? mockResult.mon.eq(mon) : null;
    }

    @Override
    public List<StudentTestSumGraphVo> getLatestRatingsOfMockTest(UserEntity userEntity) {
        String[] latestMock = findLatestMock(userEntity);
        return jpaQueryFactory.select(new QStudentTestSumGraphVo((mockResult.year.concat(mockResult.mon).as("date"))
                        , mockResult.subjectEntity.sbjCategoryEntity.nm.as("nm")
                        , mockResult.rating.as("rating")))
                .from(mockResult)
                .join(mockResult.subjectEntity.sbjCategoryEntity, sbjCategory)
                .where(mockResult.userEntity.userId.eq(userEntity.getUserId())
                        .and(sbjCategory.categoryId.in(myGrade.getCateIdForMockTest()))
                        .and(mockResult.year.eq(String.valueOf(latestMock[0])))
                        .and(mockResult.mon.eq(latestMock[1]))
                )
                .fetch();

    }

    public String[] findLatestMock(UserEntity userEntity) {

        MockResultEntity m = jpaQueryFactory.selectFrom(mockResult)
                .orderBy(mockResult.year.desc(), mockResult.mon.desc())
                .where(mockResult.userEntity.userId.eq(userEntity.getUserId()))
                .fetchFirst();
        String[] array = {m.getYear(), m.getMon()};
        return array;
    }

}
