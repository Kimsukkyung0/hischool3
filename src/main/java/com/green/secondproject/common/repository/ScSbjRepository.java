package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.ScSbjEntity;
import com.green.secondproject.common.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScSbjRepository extends JpaRepository<ScSbjEntity, Long> {
    List<ScSbjEntity> findAllBySchoolEntity(SchoolEntity schoolEntity);
}
