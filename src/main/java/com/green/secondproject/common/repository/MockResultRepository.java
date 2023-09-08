package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.MockResultEntity;
import com.green.secondproject.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MockResultRepository extends JpaRepository<MockResultEntity, Long>, MockResultRepositoryCustom {
    String[] findLatestMock(UserEntity userEntity);
}
