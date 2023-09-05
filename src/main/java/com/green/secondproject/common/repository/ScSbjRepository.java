package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.ScSbjEntity;
import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScSbjRepository extends JpaRepository<ScSbjEntity, Long> {
//    List<ScSbjEntity> findAllBySchoolEntity(SchoolEntity schoolEntity);
    List<ScSbjEntity> findAllBySchoolEntityAndGrade(SchoolEntity schoolEntity,String grade);

    @Query("SELECT u FROM ScSbjEntity u WHERE u.subjectEntity IN :list ")
    List<ScSbjEntity> findAllBySubjectIdList(List<SubjectEntity> list);

    @Query("Select u.subjectEntity.subjectId FROM ScSbjEntity u WHERE u.schoolEntity IN :schoolEntity AND u.grade = :grade")
    List<Long> findAllSubjectIdBySchoolEntityAndGrade(SchoolEntity schoolEntity, String grade);

    @Modifying
    @Query("DELETE FROM ScSbjEntity u WHERE u.subjectEntity IN :subjectEntities")
    int deleteAllBySubjectEntity(List<SubjectEntity> subjectEntities);

}

