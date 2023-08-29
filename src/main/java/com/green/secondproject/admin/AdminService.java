package com.green.secondproject.admin;

import com.green.secondproject.admin.model.*;
import com.green.secondproject.common.config.etc.EnrollState;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public SignInResultDto signIn(SignInParam p, String ip) {
        final String ADMIN = "ROLE_ADMIN";
        SchoolAdminEntity schoolAdmin = adminRepository.findByEmail(p.getEmail());

        if (schoolAdmin == null) {
            throw new RuntimeException("존재하지 않는 이메일");
        }
        if(!PW_ENCODER.matches(p.getPw(), schoolAdmin.getPw())) {
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

    public List<StudentClassVo> getStudentClass(int page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "vanEntity", "nm");      //학년 반 순으로 정렬 어케할건지 고쳐야하맘함함함
        Pageable pageable = PageRequest.of(page, 30, sort);  //페이징 처리
        List<UserEntity> entities = userRepository.findAllByAprYnAndEnrollStateAndRoleType(1, EnrollState.ENROLL, RoleType.STD, pageable);


        return entities.stream().map(item -> StudentClassVo.builder()
                        .nm(item.getNm())
                        .grade(item.getVanEntity().getGrade())
                        .classNum(item.getVanEntity().getClassNum())
                        .build())
                .toList();
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
}
