package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    UserEntity findByUserId(Long userId);
}
