package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.CareerEntity;
import com.green.secondproject.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareerRepository extends JpaRepository<CareerEntity, Long> {

    List<CareerEntity> findByUserEntity(UserEntity userEntity); //select 유저값 입력 선생님,유저 2개해야함

}
