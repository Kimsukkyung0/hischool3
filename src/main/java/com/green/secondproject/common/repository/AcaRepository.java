package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.AcaResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcaRepository extends JpaRepository<AcaResultEntity, Long> {

}
