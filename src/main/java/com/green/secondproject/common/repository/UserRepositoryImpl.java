package com.green.secondproject.common.repository;

import com.green.secondproject.common.config.etc.EnrollState;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.UserEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl {

    @Autowired
    EntityManager entityManager;

//    @Override
//    List<UserEntity> findTeachersNotEnrolledInSchool(){
//        List<UserEntity> userEntities = entityManager.createQuery("SELECT  ")
//
////        List<Post> resultList = entityManager.createQuery("SELECT p FROM Post AS p", Post.class).getResultList();
//        return userEntities;
//    }
//
//    List<UserEntity> findUserEntitiesByAprYnAndVanEntity(String schoolCode, int aprYn, EnrollState enrollState, RoleType roleType);
}
