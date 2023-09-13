package com.green.secondproject.common.repository;

import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.entity.VanEntity;
import com.green.secondproject.student.model.*;
import com.green.secondproject.teacher.model.TeacherGraphVo;

import java.util.List;

public interface AcaResultRepositoryCustom {
    List<StudentAcaResultWithIdVo> searchAcaResult(StudentAcaResultsParam param);


    List<StudentTestSumGraphVo> getLatestRatingsOfAcaTest(UserEntity userEntity);
    List<StudentSummarySubjectVo> getHighestRatingOfAcaTest(UserEntity userEntity);
    int[] getLatestTest(UserEntity userEntity);
    List<StudentTestSumGraphVo> getAcaTestGraph(UserEntity userEntity);
    double countStudentsNumByVanAndCate(Long classId, RoleType roleType, int aprYn, Long categoryId,int year, int semester, int midFinal);
    List<TeacherGraphVo> teacherAcaGraph(Long classId,Long categoryId,int year,int semester, int midFinal);
    int[] getLatestTest(Long vanId, Long categoryId);

}
