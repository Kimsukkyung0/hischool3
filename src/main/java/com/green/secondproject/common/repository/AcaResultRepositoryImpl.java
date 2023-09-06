package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.QAcaResultEntity;
import com.green.secondproject.common.entity.QSbjCategoryEntity;
import com.green.secondproject.common.entity.QSubjectEntity;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.utils.MyGradeGraphUtils;
import com.green.secondproject.student.model.QStudentAcaResultWithIdVo;
import com.green.secondproject.student.model.StudentAcaResultWithIdVo;
import com.green.secondproject.student.model.StudentAcaResultsParam;
import com.green.secondproject.student.model.StudentTestSumGraphVo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

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
    public List<StudentTestSumGraphVo> findAllByUserEntity(UserEntity userEntity){
        JPAQuery<StudentTestSumGraphVo> query = jpaQueryFactory.select(
                Projections.constructor(StudentTestSumGraphVo.class,
                        (acaResult.year.concat(acaResult.semester.stringValue()).concat(acaResult.midFinal.stringValue()).as("date"))
                        , acaResult.subjectEntity.nm,
                        acaResult.rating))
                .from(acaResult)
                .join(acaResult.subjectEntity, sbj)
                .join(acaResult.subjectEntity.sbjCategoryEntity, cate)
                .where(acaResult.userEntity.userId.eq(userEntity.getUserId())
                        .and(cate.categoryId.in(myGrade.getCateIdForAca()))
                        .andAnyOf((select(acaResult.year.concat(acaResult.semester.stringValue()).concat(acaResult.midFinal.toString()))
                                .from(acaResult)
                                .where(acaResult.year.concat(acaResult.semester.stringValue()).concat(acaResult.midFinal.toString()).eq(findLatestTest())))));
                

        return query.fetch();
    }

    public String findLatestTest(){
          String date = jpaQueryFactory
                .select(acaResult.year.concat(acaResult.semester.stringValue()).concat(acaResult.midFinal.toString()))
                .from(acaResult)
                .where(acaResult.year.concat(acaResult.semester.stringValue()).concat(acaResult.midFinal.toString())
                        .eq(acaResult.year.concat(acaResult.semester.stringValue()).concat(acaResult.midFinal.toString()).max())).fetchFirst();
        return date;
    }
}
