package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.ScSbjEntity;
import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.entity.SubjectEntity;
import com.green.secondproject.teacher.subject.model.AcaCategoryVo;
import com.green.secondproject.teacher.subject.model.AcaResultSubVo;
import com.green.secondproject.teacher.subject.model.AcaSubjectVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ScSbjRepository extends JpaRepository<ScSbjEntity, Long> {
//    List<ScSbjEntity> findAllBySchoolEntity(SchoolEntity schoolEntity);
    List<ScSbjEntity> findAllBySchoolEntityAndGrade(SchoolEntity schoolEntity,String grade);

    @Query("SELECT u FROM ScSbjEntity u WHERE u.subjectEntity IN :list ")
    List<ScSbjEntity> findAllBySubjectIdList(List<SubjectEntity> list);

    @Query("Select u.subjectEntity.subjectId FROM ScSbjEntity u WHERE u.schoolEntity IN :schoolEntity AND u.grade = :grade")
    List<Long> findAllSubjectIdBySchoolEntityAndGrade(SchoolEntity schoolEntity, String grade);

    @Transactional
    @Modifying
    @Query("DELETE FROM ScSbjEntity u WHERE u.subjectEntity IN :subjectEntities")
    int deleteAllBySubjectEntity(List<SubjectEntity> subjectEntities);

    @Query("select new com.green.secondproject.teacher.subject.model.AcaCategoryVo(sc.categoryId, sc.nm) " +
            "from ScSbjEntity ss " +
            "join SubjectEntity s on ss.subjectEntity.subjectId = s.subjectId " +
            "join SbjCategoryEntity sc on s.sbjCategoryEntity.categoryId = sc.categoryId " +
            "where ss.schoolEntity.schoolId = :schoolId and ss.grade = :grade group by sc.categoryId")
    List<AcaCategoryVo> findCategoryList(Long schoolId, String grade);

    @Query("select new com.green.secondproject.teacher.subject.model.AcaResultSubVo(" +
            "ss.subjectEntity.subjectId, ss.subjectEntity.nm) " +
            "from ScSbjEntity ss " +
            "join SubjectEntity s on ss.subjectEntity.subjectId = s.subjectId " +
            "where s.sbjCategoryEntity.categoryId = :categoryId and ss.schoolEntity.schoolId = :schoolId " +
            "and ss.grade = :grade")
    List<AcaResultSubVo> findSubList(Long categoryId, Long schoolId, String grade);
}

