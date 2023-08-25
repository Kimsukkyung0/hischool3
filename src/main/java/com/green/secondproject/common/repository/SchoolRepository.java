package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {
    SchoolEntity findByCode(String code);
    SchoolEntity findBySchoolId(Long schoolId);
}
