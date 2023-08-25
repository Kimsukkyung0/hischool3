package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.AttendanceEntity;
import com.green.secondproject.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
    List<AttendanceEntity> findAllByUserEntity(UserEntity userEntity);
}
