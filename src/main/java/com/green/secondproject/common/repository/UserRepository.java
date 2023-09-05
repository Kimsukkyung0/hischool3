package com.green.secondproject.common.repository;

import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.config.etc.EnrollState;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.entity.VanEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    UserEntity findByUserId(Long userId);

    Page<UserEntity> findAllByRoleTypeAndVanEntityIn(RoleType roleType, List<VanEntity> vanEntity, Pageable page);

    Page<UserEntity> findAllByVanEntityInAndRoleType(List<VanEntity> vanEntity, RoleType roleType, Pageable pageable);

    Page<UserEntity> findByNmContainingAndVanEntityInAndRoleType(String search, List<VanEntity> vanEntity, RoleType roleType, Pageable page);


    //규진작업
    long countByVanEntityInAndRoleTypeAndAprYn(List<VanEntity> vanEntity, RoleType roleType, int aprYn);

    List<UserEntity> findAllByVanEntityAndAprYnAndEnrollStateAndRoleType(VanEntity vanEntity, int aprYn, EnrollState enrollState, RoleType roleType);

    List<UserEntity> findAllByVanEntityAndRoleType(VanEntity vanEntity, RoleType roleType);

    List<UserEntity> findAllByVanEntityInAndRoleType(List<VanEntity> vanEntity, RoleType roleType);

    //석경작업
    @Query("SELECT u FROM UserEntity u WHERE u.vanEntity IN :vanEnti AND u.roleType = :roleType AND u.aprYn = :aprYn AND u.enrollState = :enrollState")
    Page<UserEntity> findUsersByConditions(List<VanEntity> vanEnti, RoleType roleType, int aprYn, EnrollState enrollState, Pageable pageable);

    @Query("SELECT u FROM UserEntity u WHERE u.vanEntity IN :vanEnti AND u.roleType = :roleType")
    Page<UserEntity> findUsersByVanEntityAndRoleType(List<VanEntity> vanEnti, RoleType roleType, Pageable pageable);

    @Query("SELECT u FROM UserEntity u WHERE u.vanEntity IN :vanEnti AND u.roleType = :roleType AND u.enrollState = :#{#enrollState}")
    Page<UserEntity> findUsersByVanEntityAndRoleTypeAndEnrollState(List<VanEntity> vanEnti, RoleType roleType, EnrollState enrollState, Pageable pageable);

    Page<UserEntity> findByNmContainingAndVanEntityInAndRoleTypeAndEnrollState(String search, List<VanEntity> vanEntity, RoleType roleType,EnrollState enrollState, Pageable page);


    //정민+수천 합체


    @Query("SELECT u FROM UserEntity u JOIN u.vanEntity v WHERE "
            + "(:search IS NULL OR u.nm LIKE CONCAT('%', :search, '%')) "
            + "AND (:classNum IS NULL OR v.classNum = :classNum) "
            + "AND (:grade IS NULL OR v.grade = :grade) "
            + "AND (:enrollState IS NULL OR u.enrollState = :enrollState)")
    Page<UserEntity> findByCriteria(
            @Param("search") String search,
            @Param("classNum") String classNum,
            @Param("grade") String grade,
            @Param("enrollState") EnrollState enrollState,
            Pageable pageable);
}
