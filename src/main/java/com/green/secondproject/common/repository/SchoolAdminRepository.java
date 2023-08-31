package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.SchoolAdminEntity;
import com.green.secondproject.common.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolAdminRepository extends JpaRepository<SchoolAdminEntity, Long> {
    SchoolAdminEntity findByEmail(String email);
    SchoolAdminEntity findBySchoolEntity(SchoolEntity schoolEntity);
}
