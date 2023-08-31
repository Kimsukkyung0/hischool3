package com.green.secondproject.admin;

import com.green.secondproject.admin.model.*;
import com.green.secondproject.common.config.redis.RedisService;
import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.JwtTokenProvider;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.*;
import com.green.secondproject.common.repository.*;
import com.green.secondproject.common.utils.ResultUtils;
import com.green.secondproject.sign.model.SignInParam;
import com.green.secondproject.sign.model.SignInResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.green.secondproject.common.config.etc.EnrollState.*;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final SchoolAdminRepository adminRepository;
    private final PasswordEncoder PW_ENCODER;
    private final RedisService redisService;
    private final JwtTokenProvider JWT_PROVIDER;
    private final AuthenticationFacade facade;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final VanRepository vanRepository;
    private final NoticeRepository noticeRepository;
    private Long schoolCode;

    public SignInResultDto signIn(SignInParam p, String ip) {
        final String ADMIN = "ROLE_ADMIN";
        SchoolAdminEntity schoolAdmin = adminRepository.findByEmail(p.getEmail());

        if (schoolAdmin == null) {
            throw new RuntimeException("존재하지 않는 이메일");
        }
        if (!PW_ENCODER.matches(p.getPw(), schoolAdmin.getPw())) {
            throw new RuntimeException("비밀번호 불일치");
        }

        String redisKey = String.format("c:RT(%s):ADMIN:%s:%s", "Server", schoolAdmin.getAdminId(), ip);
        if (redisService.getData(redisKey) != null) {
            redisService.deleteData(redisKey);
        }

        String accessToken = JWT_PROVIDER.generateJwtToken(String.valueOf(schoolAdmin.getAdminId()),
                Collections.singletonList(ADMIN), JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);
        String refreshToken = JWT_PROVIDER.generateJwtToken(String.valueOf(schoolAdmin.getAdminId()),
                Collections.singletonList(ADMIN), JWT_PROVIDER.REFRESH_TOKEN_VALID_MS, JWT_PROVIDER.REFRESH_KEY);

        redisService.setData(redisKey, refreshToken);

        SignInResultDto dto = SignInResultDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(ADMIN)
                .build();

        ResultUtils.setSuccessResult(dto);
        return dto;
    }

    public SchoolAdminEntity signUp(AdminParam p) {
        SchoolEntity schoolEntity = schoolRepository.findByCode(p.getSchoolCode());
        if (schoolEntity == null) {
            schoolEntity = schoolRepository.save(SchoolEntity.builder()
                    .code(p.getSchoolCode())
                    .nm(p.getSchoolNm())
                    .logo(p.getSchoolNm() + ".png")
                    .build());
        }
        return adminRepository.save(SchoolAdminEntity.builder()
                .email(p.getEmail())
                .pw(PW_ENCODER.encode(p.getPw()))
                .schoolEntity(SchoolEntity.builder()
                        .schoolId(schoolEntity.getSchoolId())
                        .build())
                .build());
    }

    public StatusVo getStatus() {
        SchoolEntity schoolEntity = schoolRepository.getReferenceById(facade.getLoginUser().getSchoolId());
        List<VanEntity> vanList = vanRepository.findAllBySchoolEntity(schoolEntity);
        long tcNum = userRepository.countByVanEntityInAndRoleTypeAndAprYn(vanList, RoleType.TC, 1);
        long stdNum = userRepository.countByVanEntityInAndRoleTypeAndAprYn(vanList, RoleType.STD, 1);
        long tcWaitingNum = userRepository.countByVanEntityInAndRoleTypeAndAprYn(vanList, RoleType.TC, 0);

        return StatusVo.builder()
                .tcNum(tcNum)
                .stdNum(stdNum)
                .tcWaitingNum(tcWaitingNum)
                .build();
    }

//    public List<StudentClassVo> getStudentClass(int page) {
//        Optional<SchoolEntity> schoolOpt = schoolRepository.findById(facade.getLoginUser().getSchoolId());
//        if (schoolOpt.isEmpty()) {
//            throw new RuntimeException("관리자 로그인 필요");
//        }
//        Sort sort = Sort.by(Sort.Direction.ASC, "vanEntity", "nm");      //학년 반 순으로 정렬 어케할건지 고쳐야하맘함함함
//        Pageable pageable = PageRequest.of(page-1, 17, sort);  //페이징 처리 -1해서 슬픔
//        List<UserEntity> entities = userRepository.findAllByAprYnAndRoleType(1, RoleType.STD, pageable);
//
//
//        List<StudentClassVo> studentList = new ArrayList<>();
//
//
//        return entities.stream().map(item -> StudentClassVo.builder()
//                        .userId(item.getUserId())
//                        .nm(item.getNm())
//                        .email(item.getEmail())
//                        .phone(item.getPhone())
//                        .enrollState(item.getEnrollState())
//                        .grade(item.getVanEntity().getGrade())
//                        .classNum(item.getVanEntity().getClassNum())
//                        .build())
//                .toList();
//    }


    public StudentClassListVo getStudentClass(int page) {
        Optional<SchoolEntity> schoolOpt = schoolRepository.findById(facade.getLoginUser().getSchoolId());
        if (schoolOpt.isEmpty()) {
            throw new RuntimeException("관리자 로그인 필요");
        }

        SchoolEntity schoolEntity = schoolOpt.get();

        Sort sort = Sort.by(Sort.Direction.ASC, "enrollState", "nm");      //학년 반 순으로 정렬 어케할건지 고쳐야하맘함함함
        Pageable pageable = PageRequest.of(page - 1, 17, sort);  //페이징 처리 -1해서 슬픔

        //Page<UserEntity> list1 = userRepository.findAllByRoleType(RoleType.STD, pageable);
        List<VanEntity> vanList = vanRepository.findAllBySchoolEntity(schoolEntity);
        Page<UserEntity> stdList = userRepository.findAllByVanEntityInAndRoleType(vanList, RoleType.STD, pageable);


//        SchoolEntity scEntity = schoolRepository.findBySchoolId(facade.getLoginUser().getSchoolId());

        List<StudentClassVo> result = new ArrayList<>();

        for (UserEntity entity : stdList) {
            VanEntity vanEntity = vanRepository.findByVanId(entity.getVanEntity().getVanId());
            result.add(StudentClassVo.builder()
                    .userId(entity.getUserId())
                    .schoolId(schoolEntity.getSchoolId())
                    .nm(entity.getNm())
                    .email(entity.getEmail())
                    .phone(entity.getPhone())
                    .enrollState(entity.getEnrollState())
                    .grade(vanEntity.getGrade())
                    .classNum(vanEntity.getClassNum())
                    .build());
        }


        return StudentClassListVo.builder()
                .list(result)
                .totalCount((int) stdList.getTotalElements())
                .totalPage(stdList.getTotalPages())
                .build();
    }


    public StudentClassListVo searchStudent(String search, int page) {
        Optional<SchoolEntity> schoolOpt = schoolRepository.findById(facade.getLoginUser().getSchoolId());
        if (schoolOpt.isEmpty()) {
            throw new RuntimeException("관리자 로그인 필요");
        }
        Sort sort = Sort.by(Sort.Direction.ASC, "vanEntity", "nm");      //학년 반 순으로 정렬 어케할건지 고쳐야하맘함함함
        Pageable pageable = PageRequest.of(page - 1, 17, sort);

        Optional<SchoolEntity> schoolEntity = schoolRepository.findById(facade.getLoginUser().getSchoolId());

        UserEntity user = new UserEntity();

        Page<UserEntity> entities = userRepository.findByNmContainingAndRoleType(search, RoleType.STD, pageable);
        Page<UserEntity> nulEntities = userRepository.findAllByRoleType(RoleType.STD, pageable);

        List<StudentClassVo> result = new ArrayList<>();


        if (search != null) {
            for (UserEntity entity : entities) {
                VanEntity vanEntity = vanRepository.findByVanId(entity.getVanEntity().getVanId());
                result.add(StudentClassVo.builder()
                        .userId(entity.getUserId())
                        .nm(entity.getNm())
                        .email(entity.getEmail())
                        .phone(entity.getPhone())
                        .enrollState(entity.getEnrollState())
                        .grade(vanEntity.getGrade())
                        .classNum(vanEntity.getClassNum())
                        .build());
            }
            return StudentClassListVo.builder()
                    .list(result)
                    .totalCount((int) entities.getTotalElements())
                    .totalPage(entities.getTotalPages())
                    .build();
        } else {
            for (UserEntity entity : nulEntities) {
                VanEntity vanEntity = vanRepository.findByVanId(entity.getVanEntity().getVanId());
                result.add(StudentClassVo.builder()
                        .userId(entity.getUserId())
                        .nm(entity.getNm())
                        .email(entity.getEmail())
                        .phone(entity.getPhone())
                        .enrollState(entity.getEnrollState())
                        .grade(vanEntity.getGrade())
                        .classNum(vanEntity.getClassNum())
                        .build());
            }
            return StudentClassListVo.builder()
                    .list(result)
                    .totalCount((int) nulEntities.getTotalElements())
                    .totalPage(nulEntities.getTotalPages())
                    .build();
        }
    }

    public UserStateUpdVo updUserState(UserStateUpdDto dto) {
        Optional<UserEntity> optEntity = userRepository.findById(dto.getUserId());

        UserEntity entity = optEntity.get();
        VanEntity.builder()
                .vanId(dto.getVanId())
                .build();

//        entity.getVanEntity().setVanId(entity.getVanEntity().getVanId());


        userRepository.save(entity);

        return UserStateUpdVo.builder()
//                .vanId(entity.getVanEntity().getVanId())
                .vanId(entity.getVanEntity().getVanId())
                .build();
    }


    public MainNoticeListVo getMainNotice() {
        SchoolEntity schoolEntity = schoolRepository.getReferenceById(facade.getLoginUser().getSchoolId());
        List<NoticeEntity> imptList = noticeRepository.findByImptYnAndSchoolEntityOrderByNoticeIdDesc(
                1, schoolEntity);
        List<NoticeEntity> normalList = noticeRepository.findTop8ByImptYnAndSchoolEntityOrderByNoticeIdDesc(
                0, schoolEntity);

        return MainNoticeListVo.builder()
                .imptList(imptList.stream().map(noticeEntity -> MainNoticeVo.builder()
                        .noticeId(noticeEntity.getNoticeId())
                        .title(noticeEntity.getTitle())
                        .hits(noticeEntity.getHits())
                        .createdAt(LocalDate.from(noticeEntity.getCreatedAt()))
                        .imptYn(noticeEntity.getImptYn())
                        .build()).toList())
                .normalList(normalList.stream().map(noticeEntity -> MainNoticeVo.builder()
                        .noticeId(noticeEntity.getNoticeId())
                        .title(noticeEntity.getTitle())
                        .hits(noticeEntity.getHits())
                        .createdAt(LocalDate.from(noticeEntity.getCreatedAt()))
                        .imptYn(noticeEntity.getImptYn())
                        .build()).toList())
                .build();
    }

    public EmergencyContacts getEmergencyContacts() {
        Optional<SchoolEntity> schoolOpt = schoolRepository.findById(facade.getLoginUser().getSchoolId());
        if (schoolOpt.isEmpty()) {
            throw new RuntimeException("관리자 로그인 필요");
        }

        SchoolEntity schoolEntity = schoolOpt.get();
        return EmergencyContacts.builder()
                .admNum(schoolEntity.getAdmNum())
                .tcNum(schoolEntity.getTcNum())
                .prcpNum(schoolEntity.getPrcpNum())
                .mainNum(schoolEntity.getMainNum())
                .machineNum(schoolEntity.getMachineNum())
                .faxNum(schoolEntity.getFaxNum())
                .build();
    }

    public EmergencyContacts updEmergencyContacts(EmergencyContacts ec) {
        Optional<SchoolEntity> schoolOpt = schoolRepository.findById(facade.getLoginUser().getSchoolId());
        if (schoolOpt.isEmpty()) {
            throw new RuntimeException("관리자 로그인 필요");
        }

        SchoolEntity schoolEntity = schoolOpt.get();
        schoolEntity.setAdmNum(ec.getAdmNum());
        schoolEntity.setTcNum(ec.getTcNum());
        schoolEntity.setPrcpNum(ec.getPrcpNum());
        schoolEntity.setMainNum(ec.getMainNum());
        schoolEntity.setMachineNum(ec.getMachineNum());
        schoolEntity.setFaxNum(ec.getFaxNum());
        schoolRepository.save(schoolEntity);

        return EmergencyContacts.builder()
                .admNum(schoolEntity.getAdmNum())
                .tcNum(schoolEntity.getTcNum())
                .prcpNum(schoolEntity.getPrcpNum())
                .mainNum(schoolEntity.getMainNum())
                .machineNum(schoolEntity.getMachineNum())
                .faxNum(schoolEntity.getFaxNum())
                .build();
    }


    public int enrollUser(long userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        userEntity.setEnrollState(ENROLL);
        userRepository.save(userEntity);
        return 1;
    }


    public int graduateUser(long userId) {
//        DelUserDto dto = new DelUserDto();
//        dto.setUserId(myuser.getUserId());
//        return mapper.delUser(dto);

        UserEntity userEntity = userRepository.findByUserId(userId);
        userEntity.setEnrollState(GRADUATION);
        userRepository.save(userEntity);
        return 1;
    }


    public int transferUser(long userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        userEntity.setEnrollState(TRANSFER);
        userRepository.save(userEntity);
        return 1;
    }


    public int leaveUser(long userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        userEntity.setEnrollState(LEAVE);
        userRepository.save(userEntity);
        return 1;
    }
}
