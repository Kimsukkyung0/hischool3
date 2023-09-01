package com.green.secondproject.admin.teachermng;

import com.green.secondproject.admin.teachermng.model.TeacherMngVo;
import com.green.secondproject.admin.teachermng.model.TeacherMngVoContainer;
import com.green.secondproject.admin.teachermng.model.TeacherMngWithPicVo;
import com.green.secondproject.admin.teachermng.model.TeacherStatUpdDto;
import com.green.secondproject.common.config.etc.EnrollState;
import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.SchoolAdminEntity;
import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.entity.VanEntity;
import com.green.secondproject.common.repository.SchoolAdminRepository;
import com.green.secondproject.common.repository.SchoolRepository;
import com.green.secondproject.common.repository.UserRepository;
import com.green.secondproject.common.repository.VanRepository;
import com.green.secondproject.sign.SignService;
import com.green.secondproject.sign.model.SchoolParam;
import com.green.secondproject.teacher.model.TeacherEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeacherMngService {


    private final UserRepository userRepository;
    private final SchoolRepository scRep;
    private final VanRepository vanRep;
    private final AuthenticationFacade facade;
    private final SchoolAdminRepository scAdminRep;
    private final SignService signService;


    @Value("${file.aprimgPath}")
    private String aprimgPath;


    public TeacherMngVoContainer teacherNotapprovedList( Pageable pageable) {
      SchoolEntity scEnti = scRep.findBySchoolId(facade.getLoginUser().getSchoolId());
        List<VanEntity> vanEnti = vanRep.findDistinctBySchoolEntity(scEnti);

        pageable = PageRequest.of(pageable.getPageNumber()-1,  16);
        Page<UserEntity> tcList = userRepository.findUsersByConditions(vanEnti, RoleType.TC, 0, EnrollState.ENROLL, pageable);

        List<TeacherMngVo> finalResult = new ArrayList<>();

        for (UserEntity en : tcList) {
            VanEntity vanEntity = vanRep.findByVanId(en.getVanEntity().getVanId());
            finalResult.add(TeacherMngVo.builder()
                    .userId(en.getUserId())
                    .schoolNm(scEnti.getNm())
                    .grade(vanEntity.getGrade())
                    .vanNum(vanEntity.getClassNum())
                    .email(en.getEmail())
                    .nm(en.getNm())
                    .birth(en.getBirth())
                    .phone(en.getPhone())
                    .address(en.getAddress())
                    .detailAddr(en.getDetailAddr())
                    .role(en.getRoleType().toString())
                    .aprYn(en.getAprYn())
                    .enrollState(en.getEnrollState()).build());
        }
        return  TeacherMngVoContainer.builder()
                .list(finalResult)
                .totalCount((int)tcList.getTotalElements())
                .totalPage(tcList.getTotalPages()).build();
    }


    public TeacherMngVoContainer teacherListOfTheSchool(Pageable pageable,String search) {

        Optional<SchoolEntity> scEntiOpt = scRep.findById(facade.getLoginUser().getSchoolId());//학교 코드로 학교 entity 가져오기
        if (scEntiOpt.isEmpty()) {
            throw new RuntimeException("관리자 로그인 필요");
        }
        SchoolEntity scEnti = scEntiOpt.get();
        pageable = PageRequest.of(pageable.getPageNumber()-1,  16);

        List<VanEntity> vanEnti = vanRep.findDistinctBySchoolEntity(scEnti);

        List<TeacherMngVo> subResult = new ArrayList<>();

        if(search==null) {
            Page<UserEntity> tcList = userRepository.findUsersByVanEntityAndRoleType(vanEnti, RoleType.TC, pageable);
            for (UserEntity en : tcList) {
                VanEntity vanEntity = vanRep.findByVanId(en.getVanEntity().getVanId());
                subResult.add(TeacherMngVo.builder()
                        .userId(en.getUserId())
                        .schoolNm(scEnti.getNm())
                        .grade(vanEntity.getGrade())
                        .vanNum(vanEntity.getClassNum())
                        .email(en.getEmail())
                        .nm(en.getNm())
                        .birth(en.getBirth())
                        .phone(en.getPhone())
                        .address(en.getAddress())
                        .detailAddr(en.getDetailAddr())
                        .role(en.getRoleType().toString())
                        .aprYn(en.getAprYn())
                        .enrollState(en.getEnrollState())
                        .build());
            }

            return TeacherMngVoContainer.builder()
                    .list(subResult)
                    .totalCount((int) tcList.getTotalElements())
                    .totalPage(tcList.getTotalPages()).build();
        }

        else{
            Page<UserEntity> tcResearchList = userRepository.findByNmContainingAndVanEntityInAndRoleType(search,vanEnti,RoleType.TC,pageable);
                for (UserEntity en : tcResearchList) {
                    VanEntity vanEntity = vanRep.findByVanId(en.getVanEntity().getVanId());
                    subResult.add(TeacherMngVo.builder()
                            .userId(en.getUserId())
                            .schoolNm(scEnti.getNm())
                            .grade(vanEntity.getGrade())
                            .vanNum(vanEntity.getClassNum())
                            .email(en.getEmail())
                            .nm(en.getNm())
                            .birth(en.getBirth())
                            .phone(en.getPhone())
                            .address(en.getAddress())
                            .detailAddr(en.getDetailAddr())
                            .role(en.getRoleType().toString())
                            .aprYn(en.getAprYn())
                            .enrollState(en.getEnrollState())
                            .build());
                }

                return TeacherMngVoContainer.builder()
                        .list(subResult)
                        .totalCount((int) tcResearchList.getTotalElements())
                        .totalPage(tcResearchList.getTotalPages()).build();
            }
    }


//잘 작동되는 친구^^
    public TeacherMngWithPicVo teacherDetailNotApr(Long userId){
        UserEntity userEnti = userRepository.findByUserId(userId);

              SchoolEntity scEnti = scRep.findByCode(userEnti.getVanEntity().getVanId().toString());

        String aprPicPath =  aprimgPath +"/" + userId + "/"+ userEnti.getAprPic();

        return TeacherMngWithPicVo.builder()
                .aprPic(aprPicPath)
                .userId(userEnti.getUserId())
                .schoolNm(userEnti.getVanEntity().getSchoolEntity().getNm())
                .grade(userEnti.getVanEntity().getGrade())
                .vanNum(userEnti.getVanEntity().getClassNum())
                .email(userEnti.getEmail())
                .nm(userEnti.getNm())
                .birth(userEnti.getBirth())
                .phone(userEnti.getPhone())
                .address(userEnti.getAddress())
                .detailAddr(userEnti.getDetailAddr())
                .role(userEnti.getRoleType().toString())
                .aprYn(userEnti.getAprYn())
                .enrollState(userEnti.getEnrollState())
                .build();

    }
        public String teacherAprv(Long teacherId,Long schoolId) {
            UserEntity getUserInfo = userRepository.findByUserId(teacherId);
            //선생님 확인과정
            if (getUserInfo == null) {
                return "유저 정보가 존재하지 않습니다";
            }

            if (getUserInfo.getVanEntity().getSchoolEntity().getSchoolId() == schoolId) {
                if (getUserInfo.getRoleType().equals(RoleType.TC)) {
                    if (getUserInfo.getAprYn() == 0) {
                        getUserInfo.setAprYn(1);
                        userRepository.save(getUserInfo);
                        return "승인처리되었습니다";
                    } else {
                        return "aprYn : " + getUserInfo.getAprYn();
                    }
                } else {
                    return "올바른 요청이 아닙니다 : " + getUserInfo.getRoleType();
                }
            } else {
                return "권한이없는 유저에 대한 요청";
            }
        }

    public TeacherMngVo teacherStatUpd (TeacherStatUpdDto dto){
        log.info("dto : {}",dto);
        Optional<VanEntity> vanEnti = null;
        Optional<SchoolEntity> scEntiOpt;
        Optional<UserEntity> tcEntiOpt;


        Long schoolId = facade.getLoginUser().getSchoolId();
        int grade = dto.getGrade();
        Long vanId = 0L;
        String notClassifiedVan = "0";
        String notClassifiedYear = "0000";


        Optional<SchoolAdminEntity> scAdminEntiOpt = Optional.of(scAdminRep.findByEmail(facade.getLoginUser().getEmail()));


            //관리자로그인 확인
            if (scAdminEntiOpt.isEmpty()
                    || !(scAdminEntiOpt.get().getSchoolEntity().getSchoolId().equals(schoolId))
                    ) {
            throw new RuntimeException("해당학교 소속 관리자 로그인 필요");

        }

            if(dto == null){
                    //dto 에 값이 들어있지않은 경우
                    throw new InvalidParameterException("올바른 값이 아닙니다");
            }
            //DTO에 값이 들어있는 경우
            else {
                //유저확인
                tcEntiOpt = userRepository.findById(dto.getUserId());
                scEntiOpt = scRep.findById(schoolId);

                if(tcEntiOpt.isEmpty()){
                     throw new EntityNotFoundException("해당하는 유저가 존재하지 않습니다");
                }

                //txEntiOpt 에 값이 담겨있는 경우
                else if(grade>=0 && grade<=3) {
                    if (dto.getGrade() == 0) {
                        vanEnti = Optional.of(vanRep.findByGradeAndSchoolEntity(String.valueOf(grade), scEntiOpt.get()));

                        //학교에 소속없음에 해당하는 학급이 존재하는지 확인
                        //존재한다면 값을 넣고
                        if (vanEnti.isPresent()) {
                            vanId = vanEnti.get().getVanId();
                        } else if (vanEnti.isEmpty()) {
                            //존재하지않는다면 삽입
                            vanId = Optional.of(vanRep.save(VanEntity.builder()
                                    .grade(notClassifiedVan)
                                    .classNum(notClassifiedVan)
                                    .year(notClassifiedYear)
                                    .build())).get().getVanId();

                        }
                    }
                    //학년 학반이 있는 경우
                    else {
                        String year = String.valueOf(LocalDate.now().getYear());
                        String strGrade = String.valueOf(grade);
                        String classNum = String.valueOf(dto.getClassNum());
                        vanEnti = Optional.of(vanRep.findBySchoolEntityAndYearAndGradeAndClassNum(scEntiOpt.get(),
                                year, strGrade, classNum));

                        if (vanEnti.isEmpty()) {
                            vanEnti = Optional.of(VanEntity.builder()
                                    .schoolEntity(scEntiOpt.get())
                                    .year(year)
                                    .grade(strGrade)
                                    .classNum(classNum)
                                    .build());
                            vanId = vanRep.save(vanEnti.get()).getVanId();
                        }
                    }
                }
                 tcEntiOpt.get().getVanEntity().setVanId(vanId);



        }
            UserEntity ue = userRepository.save(UserEntity.builder().userId(dto.getUserId()).enrollState(dto.getEnrollState()).vanEntity(vanEnti.get()).build());
            //모든 검증과정을 거치면, UPDATE!!!!!!!!!!!!!!!!
            return TeacherMngVo.builder()
                    .userId(ue.getUserId())
                    .schoolNm(ue.getNm())
                    .grade(vanEnti.get().getGrade())
                    .vanNum(vanEnti.get().getClassNum())
                    .email(ue.getEmail())
                    .nm(ue.getNm())
                    .birth(ue.getBirth())
                    .phone(ue.getPhone())
                    .address(ue.getAddress())
                    .detailAddr(ue.getDetailAddr())
                    .role(ue.getRoleType().toString())
                    .aprYn(ue.getAprYn())
                    .enrollState(ue.getEnrollState())
                    .build();

    }

//    public List<Integer> getClassListForTeacher(int grade){
//        //접속시 바로 관리자 로그인 확인
//        MyUserDetails myUser = facade.getLoginUser();
//        Optional<SchoolEntity> scEntiOpt = scRep.findById(myUser.getSchoolId());//학교 코드로 학교 entity 가져오기
//        if (scEntiOpt.isEmpty()) {
//            throw new RuntimeException("관리자 로그인 필요");
//        }
//
//        //학년이 0에서 3일경우
//        if(grade>=0 && grade<=3){
////            if(grade==0){
////                Optional<VanEntity> vanEnti = Optional.of(vanRep.findByGrade(grade));
////                String notClassifiedVan = "0";
////                Long vanId;
////
////                if(vanEnti.isPresent()){
////                    vanId = vanEnti.get().getVanId();
////                }else{
////                    vanEnti = Optional.of(vanRep.save(VanEntity.builder().grade(notClassifiedVan).classNum(notClassifiedVan).build()));
////                    vanId = vanEnti.get().getVanId();
////                }
//            }
//
//            //학년이 1-3학년일 경우
//            SchoolParam scP = new SchoolParam();
//            scP.setSchoolCode(scEntiOpt.get().getCode());
//            scP.setGrade(String.valueOf(grade));
//            List<Integer> classList = signService.getClassList(scP);
//
//            return classList;
//
//        }
//    }
    //학반 보여주는 api 끌어오기




}


