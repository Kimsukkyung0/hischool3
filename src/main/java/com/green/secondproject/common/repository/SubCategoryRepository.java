package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.SbjCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SbjCategoryEntity, Long> {
}