package com.green.secondproject.van;

import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.entity.VanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VanRepository extends JpaRepository<VanEntity, Long> {
    VanEntity findVanEntityBySchoolEntityAndYearAndGradeAndClassNum(SchoolEntity schoolEntity, String year,
                                                                    String grade, String classNum);
}
