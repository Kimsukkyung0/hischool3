package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.SbjCategoryEntity;
import com.green.secondproject.common.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    List<SubjectEntity> findBySbjCategoryEntity(SbjCategoryEntity entity);
    List<SubjectEntity> findBySbjCategoryEntityOrderByNm(SbjCategoryEntity entity);

    @Query("SELECT u FROM SubjectEntity u WHERE u.subjectId IN :#{#list}")
    List<SubjectEntity> findAllBySubjectIdList(List<Long> list);
//    List<SubjectEntity> findAllBySbjCategoryEntity(Long categoryId);



}
