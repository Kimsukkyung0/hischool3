package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.SchoolEntity;
import com.sun.mail.imap.protocol.ID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {
    SchoolEntity findByCode(String code);
    SchoolEntity findBySchoolId(Long schoolId);
    SchoolEntity findByLogo(String logo);
}
