package com.green.secondproject.van;

import com.green.secondproject.common.entity.VanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VanRepository extends JpaRepository<VanEntity, Long> {
}
