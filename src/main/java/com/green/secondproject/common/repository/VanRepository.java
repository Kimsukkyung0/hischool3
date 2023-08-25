package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.entity.VanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VanRepository extends JpaRepository<VanEntity, Long> {
    VanEntity findBySchoolEntityAndYearAndGradeAndClassNum(SchoolEntity schoolEntity, String year,
                                                                    String grade, String classNum);
    List<VanEntity> findAllBySchoolEntity(SchoolEntity schoolEntity);
}
