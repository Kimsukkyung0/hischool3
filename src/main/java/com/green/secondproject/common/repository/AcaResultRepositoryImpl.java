package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.*;
import com.green.secondproject.student.model.QStudentAcaResultWithIdVo;
import com.green.secondproject.student.model.StudentAcaResultWithIdVo;
import com.green.secondproject.student.model.StudentAcaResultsParam;
import com.green.secondproject.student.model.StudentTestSumGraphVo;
import com.querydsl.core.types.ExpressionUtils;
import com.green.secondproject.common.entity.*;
import com.green.secondproject.common.utils.MyGradeGraphUtils;
import com.green.secondproject.student.model.*;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;

@RequiredArgsConstructor
public class AcaResultRepositoryImpl implements AcaResultRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QAcaResultEntity a1 = new QAcaResultEntity("a1");
    private final QAcaResultEntity a2 = new QAcaResultEntity("a2");
    private final QAcaResultEntity a3 = new QAcaResultEntity("a3");
    private final QSbjCategoryEntity c = QSbjCategoryEntity.sbjCategoryEntity;
    private final QSubjectEntity s = QSubjectEntity.subjectEntity;
    private final QVanEntity v = QVanEntity.vanEntity;
    private final QUserEntity u = QUserEntity.userEntity;
    private final QSchoolEntity sc = QSchoolEntity.schoolEntity;
//    private final MyGradeGraphUtils myGrade;

    @Override
    public List<StudentAcaResultWithIdVo> searchAcaResult(StudentAcaResultsParam param) {
        return jpaQueryFactory
                .select(new QStudentAcaResultWithIdVo(a1.resultId, a1.year, a1.semester,
                        a1.midFinal, a1.subjectEntity.sbjCategoryEntity.nm, a1.subjectEntity.nm,
                        a1.score, a1.rating, a1.classRank, a1.wholeRank,
                        ExpressionUtils.as(JPAExpressions
                                        .select(a2.resultId.count())
                                        .from(a2)
                                        .join(a2.userEntity, u)
                                        .join(u.vanEntity, v)
                                        .where(v.vanId.eq(param.getVanId()),
                                                a2.subjectEntity.subjectId.eq(a1.subjectEntity.subjectId),
                                                a2.year.eq(a1.year), a2.semester.eq(a1.semester),
                                                a2.midFinal.eq(a1.midFinal)),"vanCnt"),
                        ExpressionUtils.as(JPAExpressions
                                        .select(a3.resultId.count())
                                        .from(a3)
                                        .join(a3.userEntity, u)
                                        .join(u.vanEntity, v)
                                        .join(v.schoolEntity, sc)
                                        .where(v.grade.eq(param.getGrade()), sc.schoolId.eq(param.getSchoolId()),
                                                a3.subjectEntity.subjectId.eq(a1.subjectEntity.subjectId),
                                                a3.year.eq(a1.year), a3.semester.eq(a1.semester),
                                                a3.midFinal.eq(a1.midFinal)),"wholeCnt")))
                .from(a1)
                .where(a1.userEntity.userId.eq(param.getUserId()),
                        yearEq(param.getYear()), semesterEq(param.getSemester()), midFinalEq(param.getMidFinal()))
                .orderBy(a1.year.desc(), a1.semester.desc(), a1.midFinal.desc())
                .fetch();
    }

    private BooleanExpression yearEq(String year) {
        return year != null ? a1.year.eq(year) : null;
    }

    private BooleanExpression semesterEq(Integer semester) {
        return semester != null ? a1.semester.eq(semester) : null;
    }

    private BooleanExpression midFinalEq(Integer midFinal) {
        return midFinal != null ? a1.midFinal.eq(midFinal) : null;
    }

    @Override
    public List<StudentTestSumGraphVo> getLatestRatingsOfAcaTest(UserEntity userEntity) {
        return jpaQueryFactory.select(new QStudentTestSumGraphVo((acaResult.year.concat(acaResult.semester.stringValue()).concat(acaResult.midFinal.stringValue()).as("date"))
                        , acaResult.subjectEntity.sbjCategoryEntity.nm.as("nm")
                        , acaResult.rating.as("rating")))
                .from(acaResult)
                .join(acaResult.subjectEntity, sbj)
                .join(acaResult.subjectEntity.sbjCategoryEntity, cate)
                .where(acaResult.userEntity.userId.eq(userEntity.getUserId())
                                .and(cate.categoryId.in(1,3,6,7))
                                .and(acaResult.year.eq(String.valueOf(findLatestTest()[0])))
                .and(acaResult.semester.eq(findLatestTest()[1]))
                .and(acaResult.midFinal.eq(findLatestTest()[2]))
                )
//                .orderBy(acaResult.year.asc(),acaResult.semester.asc(),acaResult.midFinal.asc())
//                        .and(acaResult.year.concat(String.valueOf(acaResult.semester)).concat(acaResult.midFinal.toString()).eq(findLatestTest()))
                .fetch();
    }

//    @Override
//    public List<StudentTestSumGraphVo> getLatestRatingsOfAcaTest(UserEntity userEntity) {
//        return jpaQueryFactory.select(new QStudentTestSumGraphVo((acaResult.year.concat(acaResult.semester.stringValue()).concat(acaResult.midFinal.stringValue()).as("date"))
//                        , acaResult.subjectEntity.nm.as("nm")
//                        , acaResult.rating.as("rating")))
//                .from(acaResult)
//                .join(acaResult.subjectEntity, sbj)
//                .join(acaResult.subjectEntity.sbjCategoryEntity, cate)
//                .where(acaResult.userEntity.userId.eq(userEntity.getUserId())
//                        .and(cate.categoryId.in(myGrade.getCateIdForAca()))
//                        .and(acaResult.year.loe(String.valueOf(LocalDate.now().getYear()))))
//                .orderBy(acaResult.year.asc(),acaResult.semester.asc(),acaResult.midFinal.asc())
////                        .and(acaResult.year.concat(String.valueOf(acaResult.semester)).concat(acaResult.midFinal.toString()).eq(findLatestTest()))
//                .fetch();
//    }

    public int[] findLatestTest() {
        AcaResultEntity acaResultEntity = jpaQueryFactory
                .selectFrom(acaResult)
                .orderBy(acaResult.year.desc(), acaResult.semester.desc(), acaResult.midFinal.desc()).fetchFirst();
     int[] stList = {Integer.parseInt(acaResultEntity.getYear()), acaResultEntity.getSemester(),acaResultEntity.getMidFinal()};
     return stList;
    }

    @Override
    public List<StudentSummarySubjectVo> getHighestRatingOfAcaTest(UserEntity userEntity){
        String nowYear = String.valueOf(LocalDate.now().getYear());
        return jpaQueryFactory.select(new QStudentSummarySubjectVo(
                        acaResult.subjectEntity.sbjCategoryEntity.nm.as("nm")
                        , acaResult.rating.min().as("rating")))
                .from(acaResult)
                .join(acaResult.subjectEntity, sbj)
                .join(acaResult.subjectEntity.sbjCategoryEntity, cate)
                .where(acaResult.userEntity.userId.eq(userEntity.getUserId())
                        .and(cate.categoryId.in(1,3,6,7))
                        .and(acaResult.year.loe(nowYear)))
                .orderBy(acaResult.year.asc(),acaResult.semester.asc(),acaResult.midFinal.asc())
                .groupBy(cate.nm)
                .fetch();
    }
}
