package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.entity.VanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VanRepository extends JpaRepository<VanEntity, Long> {
    VanEntity findBySchoolEntityAndYearAndGradeAndClassNum(SchoolEntity schoolEntity, String year,
                                                                    String grade, String classNum);
}
