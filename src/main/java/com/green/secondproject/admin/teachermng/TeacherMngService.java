package com.green.secondproject.admin.teachermng;

import com.green.secondproject.admin.teachermng.model.TeacherMngVo;
import com.green.secondproject.common.config.etc.EnrollState;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.entity.VanEntity;
import com.green.secondproject.common.repository.SchoolRepository;
import com.green.secondproject.common.repository.UserRepository;
import com.green.secondproject.common.repository.VanRepository;
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
    @Autowired
    SchoolRepository scRep;
    @Autowired
    VanRepository vanRep;

    public List<TeacherMngVo> teacherNotapprovedList(Long schoolId){
        SchoolEntity scEnti = scRep.findByCode(schoolId.toString());//학교 코드로 학교 entity 가져오기
        List<VanEntity> vanEnti = vanRep.findDistinctBySchoolEntity(scEnti);
        //school entity 로 반 entity 리스트 가져오기

        //(학교코드 ->반코드)   ->>RoleType : TC / apr_Yn =0 / enrollstate = enroll인 애들
//        List<UserEntity> tcList =  userRepository.findAllByVanEntityInAndRoleTypeAndAprYnAndEnrollState(vanEnti,RoleType.TC, 0, EnrollState.ENROLL);
        List<UserEntity> tcList =  userRepository.findUsersByConditions(vanEnti,RoleType.TC, 0, EnrollState.ENROLL);
        List<TeacherMngVo> finalResult = new ArrayList<>();

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


//        TeacherMngParam param = TeacherMngParam.builder().roleType(RoleType.TC).aprYn(0).enrollState(EnrollState.ENROLL).vanEntityList(vanEnti).build();
//        List<UserEntity> tcList = userRepository.findAllByRoleTypeAndAprYnAndVanEntityAndEnrollState(param);
//        List<UserEntity> tcList = userRepository.findAllByRoleTypeAndAprYnAndEnrollState(param);
    }
}
