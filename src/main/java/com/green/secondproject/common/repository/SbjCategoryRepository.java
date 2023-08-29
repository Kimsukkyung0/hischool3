package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.SbjCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SbjCategoryRepository extends JpaRepository<SbjCategoryEntity,Long> {

}
