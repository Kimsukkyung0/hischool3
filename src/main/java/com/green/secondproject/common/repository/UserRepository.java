package com.green.secondproject.common.repository;

import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.config.etc.EnrollState;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.entity.VanEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(Long userId);

    List<UserEntity> findAllByAprYnAndRoleType(int aprYn, RoleType roleType, Pageable page);
//    List<UserEntity> findAllByAprYnAndEnrollStateAndRoleType(int aprYn, EnrollState enrollState, RoleType roleType);

    List<UserEntity> findByNmContaining(String search);

    //규진작업
    long countByVanEntityInAndRoleTypeAndAprYn(List<VanEntity> vanEntity, RoleType roleType, int aprYn);
    List<UserEntity> findAllByVanEntityAndAprYnAndEnrollStateAndRoleType(VanEntity vanEntity, int aprYn, EnrollState enrollState, RoleType roleType);

    //석경작업
    @Query("SELECT u FROM UserEntity u WHERE u.vanEntity IN :vanEnti AND u.roleType = :roleType AND u.aprYn = :aprYn AND u.enrollState = :enrollState")
    Page<UserEntity> findUsersByConditions(List<VanEntity> vanEnti, RoleType roleType, int aprYn, EnrollState enrollState,Pageable pageable);

    @Query("SELECT u FROM UserEntity u WHERE u.vanEntity IN :vanEnti AND u.roleType = :roleType")
    Page<UserEntity> findUsersByVanEntityAndRoleType(List<VanEntity> vanEnti,RoleType roleType,Pageable pageable);
}
