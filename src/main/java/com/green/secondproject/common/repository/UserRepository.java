package com.green.secondproject.common.repository;

import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.entity.VanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(Long userId);

    //규진작업
    long countByVanEntityInAndRoleTypeAndAprYn(List<VanEntity> vanEntity, RoleType roleType, int aprYn);

    //석경작업
    //List<UserEntity> findAllByRoleTypeAnd();
    List<UserEntity> findAllByRoleTypeAndAprYn(RoleType roleType,int aprYn);
}
