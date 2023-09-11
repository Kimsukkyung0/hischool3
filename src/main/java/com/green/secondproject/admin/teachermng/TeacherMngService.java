package com.green.secondproject.admin.teachermng;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.secondproject.admin.teachermng.model.*;
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
import com.green.secondproject.common.utils.ApiUtils;
import com.green.secondproject.sign.SignService;
import com.green.secondproject.sign.model.ClassVo;
import com.green.secondproject.sign.model.SchoolParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Collections;
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
    @Value("${my-api.key}")
    private String myApiKey;

    Page<UserEntity> tcList;


    public TeacherMngVoContainer teacherNotapprovedList(Pageable pageable) {
        SchoolEntity scEnti = scRep.findBySchoolId(facade.getLoginUser().getSchoolId());
        List<VanEntity> vanEnti = vanRep.findDistinctBySchoolEntity(scEnti);

        pageable = PageRequest.of(pageable.getPageNumber() - 1, 16);
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
        return TeacherMngVoContainer.builder()
                .list(finalResult)
                .totalCount((int) tcList.getTotalElements())
                .totalPage(tcList.getTotalPages()).build();
    }


    public TeacherMngVoContainer teacherListOfTheSchool(Optional<TeacherListDto> dto2) {
//        Pageable pageable, String search, EnrollState enrollState

        Optional<SchoolEntity> scEntiOpt = scRep.findById(facade.getLoginUser().getSchoolId());//학교 코드로 학교 entity 가져오기
        if (scEntiOpt.isEmpty()) {
            throw new RuntimeException("관리자 로그인 필요");
        }

        SchoolEntity scEnti = scEntiOpt.get();


        List<VanEntity> vanEnti = vanRep.findDistinctBySchoolEntity(scEnti);

        List<TeacherMngVo> subResult = new ArrayList<>();

        if(dto2.isPresent()){
            TeacherListDto dto = dto2.get();
            log.info("dto : {}", dto);
            Pageable pageable = PageRequest.of(dto.getPage() - 1, 16);
        if(dto.getSearch() != null || dto.getEnrollState()!=null) {
            if (dto.getSearch() != null && dto.getEnrollState() == null)//검색어 O
            {
                tcList = userRepository.findByCase2(dto.getSearch(), vanEnti, RoleType.TC, pageable);
                log.info("case 2 : {검색어만 존재}");
            } else if (dto.getEnrollState() != null && dto.getSearch() == null) {//역할필터링 O
                tcList = userRepository.findByCase3(vanEnti, RoleType.TC,dto.getEnrollState(), pageable);
                log.info("case 3 : {상태값만 존재}");
            } else {tcList = userRepository.findByCase1
                    (dto.getSearch(), vanEnti, RoleType.TC, dto.getEnrollState(), pageable);
                log.info("case 1 : {멀티 필터링}");}
        }
        }
        else if(dto2.isEmpty()) {//둘다 없을때
            Pageable pageable = PageRequest.of(1,16);
            tcList = userRepository.findByCase4(vanEnti, RoleType.TC, pageable);
            log.info("case 4 : {둘다 없을때-기본정렬}");
        }

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


    public TeacherMngWithPicVo teacherDetailNotApr(Long userId) {

        Optional<SchoolEntity> scEntiOpt = scRep.findById(facade.getLoginUser().getSchoolId());//학교 코드로 학교 entity 가져오기
        if (scEntiOpt.isEmpty()) {
            throw new RuntimeException("관리자 로그인 필요");
        }
        Optional<UserEntity> userEntiOpt = Optional.of(userRepository.findByUserId(userId));

        if (userEntiOpt.isEmpty()){
            throw new RuntimeException("관리자 로그인 필요");
        }
        else {
            UserEntity userEnti = userEntiOpt.get();
            String aprPicPath = aprimgPath + "/" + userId + "/" + userEnti.getAprPic();

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

    }

    public String teacherAprv(Long teacherId, Long schoolId) {
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
            throw new RuntimeException("권한이없는 유저에 대한 요청");
        }
    }

    public List<Integer> getClassListForTeacher(int grade, int year) {
        int thisYear = LocalDate.now().getYear();
        String strThisYear = String.valueOf(thisYear);
        String strYear = String.valueOf(year);

        //접속시 바로 관리자 로그인 확인
        MyUserDetails myUser = facade.getLoginUser();

        //학교 코드로 학교 entity 가져오기
        Optional<SchoolEntity> scEntiOpt = scRep.findById(myUser.getSchoolId());
        log.info(scEntiOpt.get().getNm());

        if (scEntiOpt.isEmpty()) {
            throw new RuntimeException("관리자 로그인 필요");
        }

        List<Integer> classList = new ArrayList<>();
        //학년이 0에서 3일경우
        if ((grade >= 0 && grade <= 3) && ((strYear.equals(strThisYear) || year == thisYear + 1))) {
            if (grade == 0) {

                List<Integer> unClassified = new ArrayList<>();
                unClassified.add(0);
                return unClassified;

            } else {//학년이 1-3학년일 경우

                SchoolParam scP = new SchoolParam();
                scP.setSchoolCode(scEntiOpt.get().getCode());
                scP.setGrade(String.valueOf(grade));
                classList = getClassList(scP, year);
            }
        } else {
            throw new RuntimeException("올바른 연도 혹은 학년 값이 아닙니다");
        }


        return classList;
    }

    public List<Integer> getClassList(SchoolParam p, int year) {

        List<Integer> list = new ArrayList<>();

        //올해 혹은 후년인지 검사
        if (p.toString() != null) {
            String json = ApiUtils.createWebClient().get().uri(uriBuilder -> uriBuilder.path("/classInfo")
                            .queryParam("KEY", myApiKey)
                            .queryParam("Type", "json")
                            .queryParam("pIndex", 1)
                            .queryParam("pSize", 500)
                            .queryParam("ATPT_OFCDC_SC_CODE", "D10")
                            .queryParam("SD_SCHUL_CODE", p.getSchoolCode())
                            .queryParam("AY", year)
                            .queryParam("GRADE", p.getGrade())
                            .build()
                    ).retrieve().bodyToMono(String.class)
                    .block();

            ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<String> classList = new ArrayList<>();
            try {
                JsonNode jsonNode = om.readTree(json);
                List<ClassVo> classVoList = om.convertValue(jsonNode.at("/classInfo/1/row"), new TypeReference<>() {
                });
                for (ClassVo vo : classVoList) {
                    classList.add(vo.getClassNm());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (classList.isEmpty()) {
                throw new RuntimeException("해당연도에 해당하는 학반 값이 없습니다");
            }

            list = classList.stream()
                    .map(Integer::parseInt)
                    .toList();
            List<Integer> mutableList = new ArrayList<>(list); // 불변 리스트를 수정 가능한 리스트로 복사
            Collections.sort(mutableList);


            return mutableList;

        } else {
            throw new RuntimeException("해당연도에 해당하는 학반 값이 없습니다");
        }
    }

    public List<String> getTeacherStatList() {
        List<String> teacherStatList = new ArrayList<>();
        teacherStatList.add(EnrollState.ENROLL.toString());
        teacherStatList.add(EnrollState.GRADUATION.toString());
        teacherStatList.add(EnrollState.TRANSFER.toString());
        teacherStatList.add(EnrollState.LEAVE.toString());
        return teacherStatList;
    }

    public TeacherMngVo teacherStatUpd(TeacherStatUpdDto dto) {
        Long schoolId = facade.getLoginUser().getSchoolId();
        int grade = dto.getGrade();

        log.info("dto.getUserId : {}", dto.getUserId());
        log.info("grade : {}", grade);
        log.info("dto.getClassNum : {}", dto.getClassNum());
        log.info("dto.getEnrollState : {}", dto.getEnrollState());

        Optional<UserEntity> tcEntiOpt = userRepository.findById(dto.getUserId());
        Optional<SchoolEntity> scEntiOpt = Optional.ofNullable(tcEntiOpt.get().getVanEntity().getSchoolEntity());
        Optional<SchoolAdminEntity> scAdminEntiOpt = Optional.of(scAdminRep.findByEmail(facade.getLoginUser().getEmail()));

        //아직 모르는 반변경
//        Optional<VanEntity> vanEnti;

        //만약, 0반이 존재하지않았을대 insert용
        Long vanId = 0L;
        String notClassifiedVan = "0";
        String notClassifiedYear = "0000";

        //관리자로그인 확인
        if (!scAdminEntiOpt.get().getSchoolEntity().getSchoolId().equals(schoolId)) {

            throw new RuntimeException("해당학교 소속 관리자 로그인 필요");

        } else if (tcEntiOpt.isEmpty() || tcEntiOpt.get().getRoleType().equals(RoleType.STD)) {

            throw new RuntimeException("수정대상 유저가 아닙니다");

        } else if (grade >= 0 && grade <= 3) { //입력된 학년 값이 0에서 3학년일 경우 이 구문이 실행됨


            //case 1: 0반이고 학교에 0반이 없을 경우
            //case 2: 0반이고 학교에 0반이 있 경우
            if (grade == 0) {
                VanEntity vanEnti = vanRep.findByGradeAndSchoolEntity(String.valueOf(grade), scEntiOpt.get());
                if (vanEnti == null) {
                    VanEntity newVan = VanEntity.builder().grade(notClassifiedVan).classNum(notClassifiedVan).year(notClassifiedYear).schoolEntity(scEntiOpt.get()).build();
                    vanEnti = vanRep.save(newVan);
                    vanId = vanEnti.getVanId();

                    log.info("case 1 / vanId : {}", vanId);
                } else {
                    vanId = vanEnti.getVanId();
                    log.info("case 2 / vanId : {}", vanId);
                }
            }


            //case 3: 1-3학년인데 해당연도에 학반이 없을 경우
            //case 4: 1-3학년인데 해당연도에 학반이 있을 경우
            else {
                VanEntity vanEnti =
                        vanRep.findByGradeAndClassNumAndYearAndSchoolEntity(String.valueOf(grade), String.valueOf(dto.getClassNum()), dto.getYear(), scEntiOpt.get());
                if (vanEnti == null) {
                    vanEnti = vanRep.save(VanEntity.builder()
                            .schoolEntity(scEntiOpt.get())
                            .year(dto.getYear())
                            .grade(String.valueOf(grade))
                            .classNum(String.valueOf(dto.getClassNum()))
                            .build());
                    vanId = vanEnti.getVanId();
                    log.info("case 3 / vanId : {}", vanId);
                } else {
                    vanId = vanEnti.getVanId();
                    log.info("case 4 / vanId : {}", vanId);
                }
            }
            log.info("FIN vanId : {}", vanId);


        }
        VanEntity newVan = vanRep.findByVanId(vanId);
        tcEntiOpt.get().setEnrollState(dto.getEnrollState());
        tcEntiOpt.get().setVanEntity(newVan);
        //더티체킹 확인. - 안되는 이유 ? ? ?
        log.info(tcEntiOpt.get().toString());
        UserEntity savedUser = userRepository.save(tcEntiOpt.get());
        return TeacherMngVo.builder()
                .userId(savedUser.getUserId())
                .schoolNm(scEntiOpt.get().getNm())
                .enrollState(savedUser.getEnrollState())
                .grade(newVan.getGrade())
                .vanNum(newVan.getClassNum())
                .email(savedUser.getEmail())
                .nm(savedUser.getNm())
                .birth(savedUser.getBirth())
                .phone(savedUser.getPhone())
                .address(savedUser.getAddress())
                .detailAddr(savedUser.getDetailAddr())
                .role(savedUser.getRoleType().toString())
                .aprYn(savedUser.getAprYn())
                .enrollState(savedUser.getEnrollState()).build();

    }


}


