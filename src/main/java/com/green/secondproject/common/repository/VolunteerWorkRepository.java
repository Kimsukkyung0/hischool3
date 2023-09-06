package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.entity.VolunteerWorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VolunteerWorkRepository extends JpaRepository<VolunteerWorkEntity,Long> {

    List<VolunteerWorkEntity> findAllByUserEntityAndGrade(UserEntity userEntity, String grade);

    @Query("SELECT sum(u.hrs) FROM VolunteerWorkEntity u WHERE u.userEntity = :userEntity")
    int findTotalHrsByUserEntity(UserEntity userEntity);
}
