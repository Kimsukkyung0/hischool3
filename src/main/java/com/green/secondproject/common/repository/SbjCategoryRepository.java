package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.SbjCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SbjCategoryRepository extends JpaRepository<SbjCategoryEntity,Long> {
//    List<SbjCategoryEntity> findAllByTypeIs(int type);
    List<SbjCategoryEntity> findAllByTypeIsOrderByNm(int type);

//    @Query("SELECT u FROM SbjCategoryEntity u WHERE u.SubjectEntity = :subjectId")
//    SbjCategoryEntity findBySubjectId(Long SubjectId);

    //2차작업

    List<SbjCategoryEntity> findByType(int type);
}
