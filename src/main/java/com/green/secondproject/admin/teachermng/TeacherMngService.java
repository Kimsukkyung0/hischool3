package com.green.secondproject.admin.teachermng;

import com.green.secondproject.admin.teachermng.model.TeacherMngVo;
import com.green.secondproject.common.config.etc.EnrollState;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.repository.UserRepository;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.metamodel.Metamodel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TeacherMngService {

    @Autowired
    UserRepository userRepository;

    public List<TeacherMngVo> teacherNotapprovedList(Long schoolId){
        List<UserEntity> tcList = userRepository.findAllByRoleTypeAndAprYn(RoleType.TC,0);

//        List<UserEntity> subResult = userRepository.findUserEntitiesByRoleTypeAndAprYn(schoolCode,0, EnrollState.ENROLL,RoleType.TC);
        List<TeacherMngVo> finalResult = new ArrayList<>();
        //(학교코드 ->반코드)   ->>RoleType : TC / apr_Yn =0 / enrollstate = enroll인 애들

        for(UserEntity en : tcList){
            finalResult.add(TeacherMngVo.builder()
                    .userId(en.getUserId())
                    .classId(en.getVanEntity().getVanId())
                    .email(en.getEmail())
                    .nm(en.getNm())
                    .birth(en.getBirth())
                    .phone(en.getPhone())
                    .address(en.getAddress())
                    .detailAddr(en.getDetailAddr())
                    .role(en.getRoleType().toString())
                    .aprYn(en.getAprYn())
                    .enrollState(en.getEnrollState()).build());
        } //->람돠도 가넝 ?
        return finalResult;


    }
}
