package com.green.secondproject.common.repository;

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
    private final QAcaResultEntity acaResult = QAcaResultEntity.acaResultEntity;
    private final QSbjCategoryEntity cate = QSbjCategoryEntity.sbjCategoryEntity;
    private final QSubjectEntity sbj = QSubjectEntity.subjectEntity;
    private final MyGradeGraphUtils myGrade;

    @Override
    public List<StudentAcaResultWithIdVo> searchAcaResult(StudentAcaResultsParam param) {
        return jpaQueryFactory
                .select(new QStudentAcaResultWithIdVo(acaResult.resultId, acaResult.year, acaResult.semester,
                        acaResult.midFinal, acaResult.subjectEntity.sbjCategoryEntity.nm, acaResult.subjectEntity.nm,
                        acaResult.score, acaResult.rating, acaResult.classRank, acaResult.wholeRank))
                .from(acaResult)
                .where(acaResult.userEntity.userId.eq(param.getUserId()),
                        yearEq(param.getYear()), semesterEq(param.getSemester()), midFinalEq(param.getMidFinal()))
                .orderBy(acaResult.year.desc(), acaResult.semester.desc(), acaResult.midFinal.desc())
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
}
