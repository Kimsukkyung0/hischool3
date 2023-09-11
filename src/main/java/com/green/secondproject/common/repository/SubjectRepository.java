package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.SbjCategoryEntity;
import com.green.secondproject.common.entity.SubjectEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    List<SubjectEntity> findBySbjCategoryEntity(SbjCategoryEntity entity);
//    List<SubjectEntity> findBySbjCategoryEntityOrderByNm(SbjCategoryEntity entity);

    @Query("SELECT u FROM SubjectEntity u WHERE u.subjectId IN :#{#list}")
    List<SubjectEntity> findAllBySubjectIdList(List<Long> list);
//    List<SubjectEntity> findAllBySbjCategoryEntity(Long categoryId);

    @Query("SELECT s FROM SubjectEntity s WHERE s.sbjCategoryEntity = :category AND s.subjectId NOT IN (SELECT sc.subjectEntity.subjectId FROM ScSbjEntity sc WHERE sc.schoolEntity.schoolId = :schoolId)")
    List<SubjectEntity> findSubjectsNotInScSbjByCategoryAndSchoolId(@Param("category") SbjCategoryEntity category, @Param("schoolId") Long schoolId);
}
