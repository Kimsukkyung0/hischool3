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

public interface UserRepository extends JpaRepository<UserEntity, Long>, UserRepositoryCustom {
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

    @Query("SELECT u FROM UserEntity u WHERE u.vanEntity IN :vanEnti AND u.roleType = :roleType AND u.aprYn = :aprYn AND u.enrollState = :enrollState order by u.nm")
    Page<UserEntity> findUsersByConditions(List<VanEntity> vanEnti, RoleType roleType, int aprYn, EnrollState enrollState, Pageable pageable);


    //case1 :
    @Query("SELECT u FROM UserEntity u WHERE u.vanEntity IN :vanEntity AND u.roleType = :roleType and u.nm like %:#{#search}% AND u.enrollState = :enrollState and u.aprYn = :aprYn " +
            "ORDER BY u.enrollState asc ,u.vanEntity.grade asc ,u.vanEntity.classNum asc ,u.nm asc")
    Page<UserEntity> findByCase1(String search, List<VanEntity> vanEntity, RoleType roleType, EnrollState enrollState, Pageable page,int aprYn);

    //case2 :
    @Query("SELECT u FROM UserEntity u WHERE u.vanEntity IN :vanEntity AND u.roleType = :roleType and u.nm like %:#{#search}% and u.aprYn = :aprYn " +
            "ORDER BY u.enrollState asc ,u.vanEntity.grade asc ,u.vanEntity.classNum asc, u.nm asc")
    Page<UserEntity> findByCase2(String search, List<VanEntity> vanEntity, RoleType roleType, Pageable page,int aprYn);

    //case 3 :
    @Query("SELECT u FROM UserEntity u WHERE u.vanEntity IN :vanEntity AND u.roleType = :roleType AND u.enrollState = :#{#enrollState} and u.aprYn = :aprYn ORDER BY u.enrollState asc ,u.vanEntity.grade asc ,u.vanEntity.classNum asc, u.nm asc")
    Page<UserEntity> findByCase3(List<VanEntity> vanEntity, RoleType roleType, EnrollState enrollState, Pageable pageable,int aprYn);

    //case 4 :
    @Query("SELECT u FROM UserEntity u WHERE u.vanEntity IN :vanEntity AND u.roleType = :roleType and u.aprYn = :aprYn ORDER BY u.enrollState asc ,u.vanEntity.grade asc ,u.vanEntity.classNum asc, u.nm asc")
    Page<UserEntity> findByCase4(List<VanEntity> vanEntity, RoleType roleType, Pageable pageable,int aprYn);



    //정민+수천 합체

    @Query("SELECT u FROM UserEntity u JOIN u.vanEntity v WHERE "
            + "(:search IS NULL OR u.nm LIKE CONCAT('%', :search, '%')) "
            + "AND (:classNum IS NULL OR v.classNum = :classNum) "
            + "AND (:grade IS NULL OR v.grade = :grade) "
            + "AND (:enrollState IS NULL OR u.enrollState = :enrollState)"
            + "AND (u.aprYn = :#{#aprYn})"
            + "AND (u.roleType = 'STD')")
    Page<UserEntity> findByCriteria(
            @Param("search") String search,
            @Param("classNum") String classNum,
            @Param("grade") String grade,
            @Param("aprYn") int aprYn,
            @Param("enrollState") EnrollState enrollState,
            Pageable pageable);


    @Query("SELECT count(*) FROM UserEntity u JOIN u.vanEntity v WHERE "
            + "(:search IS NULL OR u.nm LIKE CONCAT('%', :search, '%')) "
            + "AND (:classNum IS NULL OR v.classNum = :classNum) "
            + "AND (:grade IS NULL OR v.grade = :grade) "
            + "AND (:enrollState IS NULL OR u.enrollState = :enrollState)"
            + "AND (u.aprYn = :#{#aprYn})"
            + "AND (u.roleType = 'STD')"
            + "AND (v.schoolEntity.schoolId = :schoolId)")
    long countByCriteria(
            @Param("search") String search,
            @Param("classNum") String classNum,
            @Param("grade") String grade,
            @Param("enrollState") EnrollState enrollState,
            @Param("aprYn") int aprYn,
            @Param("schoolId") long schoolId);
}
