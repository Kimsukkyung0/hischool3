package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.*;
import com.green.secondproject.common.utils.MyGradeGraphUtils;
import com.green.secondproject.common.entity.QMockResultEntity;
import com.green.secondproject.common.entity.QSbjCategoryEntity;
import com.green.secondproject.student.model.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class MockResultRepositoryImpl implements MockResultRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final MyGradeGraphUtils myGrade;
    private final QMockResultEntity m = QMockResultEntity.mockResultEntity;
    private final QSbjCategoryEntity c = QSbjCategoryEntity.sbjCategoryEntity;
    private final QSubjectEntity s = QSubjectEntity.subjectEntity;

    @Override
    public List<StudentMockSumResultWithIdVo> searchMockResult(StudentSummarySubjectDto dto) {
        return jpaQueryFactory
                .select(new QStudentMockSumResultWithIdVo(m.resultId, m.year, m.mon,
                        m.subjectEntity.sbjCategoryEntity.nm, m.subjectEntity.nm,
                        m.standardScore, m.rating, m.percent))
                .from(m)
                .where(m.userEntity.userId.eq(dto.getUserId()),
                        yearEq(dto.getYear()), monEq(dto.getMon()))
                .orderBy(m.year.desc(), m.mon.desc(),
                        m.subjectEntity.sbjCategoryEntity.nm.asc())
                .fetch();
    }

    @Override
    public List<StudentSummarySubjectVo> getHighestRatingsOfMockTest(Long userId) {
        return jpaQueryFactory
                .select(new QStudentSummarySubjectVo(c.nm,
                        m.rating.min()))
                .from(m)
                .innerJoin(m.subjectEntity.sbjCategoryEntity, c)
                .where(m.userEntity.userId.eq(userId),
                        c.categoryId.in(JPAExpressions
                                .select(c.categoryId)
                                .from(c)
                                .where(c.nm.in(
                                        "국어","수학","영어","한국사"),
                                c.type.eq(2))))
                .groupBy(c.nm)
                .fetch();
    }

    private BooleanExpression yearEq(String year) {
        return year != null ? m.year.eq(year) : null;
    }

    private BooleanExpression monEq(String mon) {
        return mon != null ? m.mon.eq(mon) : null;
    }

    @Override
    public List<StudentTestSumGraphVo> getLatestRatingsOfMockTest(UserEntity userEntity) {
        String[] latestMock = findLatestMock(userEntity);
        return jpaQueryFactory.select(new QStudentTestSumGraphVo((m.year.concat(m.mon).as("date"))
                        , m.subjectEntity.sbjCategoryEntity.nm.as("nm")
                        , m.rating.as("rating")))
                .from(m)
                .join(m.subjectEntity.sbjCategoryEntity, c)
                .where(m.userEntity.userId.eq(userEntity.getUserId())
                        .and(c.categoryId.in(myGrade.getCateIdForMockTest()))
                        .and(m.year.eq(String.valueOf(latestMock[0])))
                        .and(m.mon.eq(latestMock[1]))
                )
                .fetch();

    }

    public String[] findLatestMock(UserEntity userEntity) {

        MockResultEntity mockEnti = jpaQueryFactory.selectFrom(m)
                .orderBy(m.year.desc(), m.mon.desc())
                .where(m.userEntity.userId.eq(userEntity.getUserId()))
                .fetchFirst();
        String[] array = {mockEnti.getYear(), mockEnti.getMon()};
        return array;
    }


    public List<StudentTestSumGraphVo> getMockTestGraph(UserEntity userEntity){
        LocalDate now = LocalDate.now();
        String year = String.valueOf(now.getYear());
        String mon = String.valueOf(now.getMonthValue());
        return jpaQueryFactory.select(
                        new QStudentTestSumGraphVo((m.year.concat(m.mon.stringValue()).as("date"))
                                ,m.subjectEntity.sbjCategoryEntity.nm.as("nm")
                                , m.rating.as("rating")))
                .from(m)
                .join(m.subjectEntity, s)
                .join(m.subjectEntity.sbjCategoryEntity,c)
                .where(m.userEntity.userId.eq(userEntity.getUserId())
                        .and(c.categoryId.in(myGrade.getCateIdForMockTest()))
                        .and(m.year.eq(year))
                        .and(m.mon.loe(mon)))
                .orderBy(m.year.asc(),m.mon.asc())
                .fetch();
    }

//
//
//
//        <select id = "selAcaTestResultByDatesAndPeriod">
//    SELECT A.year, A.semester, A.mid_final midFinal, C.nm cateName, B.nm nm,
//    A.score, A.rating, A.class_rank classRank, A.whole_rank wholeRank
//    FROM aca_result A
//    INNER JOIN subject B
//    ON A.subject_id = B.subject_id
//    INNER JOIN sbj_category C
//    ON C.category_id = B.category_id
//    where A.user_id = #{userId}
//        <if test='year != null and year != ""'>
//    AND A.year = #{year}
//        </if>
//        <if test='semester != null and semester != ""'>
//    AND A.semester = #{semester}
//        </if>
//        <if test='midFinal != null and midFinal != ""'>
//    AND A.mid_final = #{midFinal}
//        </if>
//    ORDER BY A.year desc , A.semester desc, midFinal desc
//            </select>
//

}
