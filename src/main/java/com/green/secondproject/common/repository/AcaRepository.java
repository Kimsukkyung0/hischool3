package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.AcaResultEntity;
import com.green.secondproject.common.entity.SubjectEntity;
import com.green.secondproject.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcaRepository extends JpaRepository<AcaResultEntity, Long> {
    List<AcaResultEntity> findAllByUserEntityInAndSemesterAndSubjectEntityAndMidFinalAndYearOrderByScoreDesc(
            List<UserEntity> userEntity, int semester, SubjectEntity subjectEntity, int midFinal, String year);
}
