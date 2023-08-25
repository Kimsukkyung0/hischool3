package com.green.secondproject.admin.teachermng;

import com.green.secondproject.admin.teachermng.model.TeacherMngVo;
import com.green.secondproject.common.config.etc.EnrollState;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class TeacherMngService {

    @Autowired
    UserRepository userRepository;

//    public List<TeacherMngVo> teacherNotapprovedList(){
//        log.info("RoleType.TC : {}",RoleType.TC);
//        String schoolCode ="7240099";
//
//        List<UserEntity> subResult = userRepository.findUserEntitiesByRoleTypeAndAprYn(schoolCode,0, EnrollState.ENROLL,RoleType.TC);
//        log.info("subResult : {}", subResult);
//        List<TeacherMngVo> finalResult = new ArrayList<>();
//        for(UserEntity en : subResult){
//            finalResult.add(TeacherMngVo.builder()
//                    .userId(en.getUserId())
//                    .classId(en.getVanEntity().getVanId())
//                    .email(en.getEmail())
//                    .nm(en.getNm())
//                    .birth(en.getBirth())
//                    .phone(en.getPhone())
//                    .address(en.getAddress())
//                    .detailAddr(en.getDetailAddr())
//                    .role(en.getRoleType().toString())
//                    .aprYn(en.getAprYn())
//                    .enrollState(en.getEnrollState()).build());
//        }
//        return finalResult;
//
//
//    }
}
