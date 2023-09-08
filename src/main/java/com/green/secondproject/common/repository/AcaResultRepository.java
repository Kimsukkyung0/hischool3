package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.AcaResultEntity;
import com.green.secondproject.common.entity.SubjectEntity;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.student.model.StudentTestSumGraphVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface AcaResultRepository extends JpaRepository<AcaResultEntity, Long>, AcaResultRepositoryCustom {
    List<AcaResultEntity> findAllByUserEntityInAndSemesterAndSubjectEntityAndMidFinalAndYearOrderByScoreDesc(
            List<UserEntity> userEntity, int semester, SubjectEntity subjectEntity, int midFinal, String year);
    long countByUserEntityInAndSubjectEntityAndYearAndSemesterAndMidFinal(Collection<UserEntity> userEntity,
                SubjectEntity subjectEntity, String year, int semester, int midFinal);
}
