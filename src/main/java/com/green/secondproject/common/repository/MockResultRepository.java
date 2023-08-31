package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.MockResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MockResultRepository extends JpaRepository<MockResultEntity, Long>, MockResultRepositoryCustom {
}
