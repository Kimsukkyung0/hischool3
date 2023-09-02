package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.entity.VanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VanRepository extends JpaRepository<VanEntity, Long> {
    VanEntity findBySchoolEntityAndYearAndGradeAndClassNum(SchoolEntity schoolEntity, String year,
                                                                    String grade, String classNum);
    List<VanEntity> findAllBySchoolEntity(SchoolEntity schoolEntity);
    List<VanEntity> findAllBySchoolEntityAndGradeAndYear(SchoolEntity schoolEntity, String grade, String year);
    List<VanEntity> findDistinctBySchoolEntity(SchoolEntity schoolEntity);
    VanEntity findByVanId(Long vanId);
    VanEntity findByGradeAndSchoolEntity(String grade, SchoolEntity schoolEntity);
    VanEntity findByGradeAndClassNumAndYearAndSchoolEntity(String grade, String classNum, String year, SchoolEntity schoolEntity);
}
