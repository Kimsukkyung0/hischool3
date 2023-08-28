package com.green.secondproject.attendance;

import com.green.secondproject.attendance.model.AttendanceIns;
import com.green.secondproject.attendance.model.AttendanceUpd;
import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.AttendanceEntity;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.repository.AttendanceRepository;
import com.green.secondproject.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository repository;
    private final UserRepository userRepository;
    private final AuthenticationFacade facade;

    public AttendanceEntity insAttendance(AttendanceIns p) {
        UserEntity user = userRepository.getReferenceById(p.getUserId());
        return repository.save(AttendanceEntity.builder()
                .userEntity(user)
                .grade(p.getGrade())
                .lessonNum(p.getLessonNum())
                .diseaseAbsence(p.getDiseaseAbsence())
                .unauthAbsence(p.getUnauthAbsence())
                .etcAbsence(p.getEtcAbsence())
                .diseaseLate(p.getDiseaseLate())
                .unauthLate(p.getUnauthLate())
                .etcLate(p.getEtcLate())
                .diseaseEarly(p.getDiseaseEarly())
                .unauthEarly(p.getUnauthEarly())
                .etcEarly(p.getEtcEarly())
                .diseaseOut(p.getDiseaseOut())
                .unauthOut(p.getUnauthOut())
                .etcOut(p.getEtcOut())
                .specialNote(p.getSpecialNote())
                .build());
    }

    public List<AttendanceEntity> getAttendance(Long userId) {
        UserEntity user = userRepository.getReferenceById(facade.getLoginUserPk());
        if (user.getRoleType() == RoleType.TC) {
            if (userId == null) {
                throw new RuntimeException("학생 PK값 필요");
            }
        } else {
            userId = facade.getLoginUserPk();
        }

        UserEntity std = userRepository.getReferenceById(userId);
        return repository.findAllByUserEntity(std);
    }

    public AttendanceEntity updAttendance(AttendanceUpd p) {
        AttendanceEntity at = repository.getReferenceById(p.getAttendId());
        at.setLessonNum(p.getLessonNum());
        at.setDiseaseAbsence(p.getDiseaseAbsence());
        at.setUnauthAbsence(p.getUnauthAbsence());
        at.setEtcAbsence(p.getEtcAbsence());
        at.setDiseaseLate(p.getDiseaseLate());
        at.setUnauthLate(p.getUnauthLate());
        at.setEtcLate(p.getEtcLate());
        at.setDiseaseEarly(p.getDiseaseEarly());
        at.setUnauthEarly(p.getUnauthEarly());
        at.setEtcEarly(p.getEtcEarly());
        at.setDiseaseOut(p.getDiseaseOut());
        at.setUnauthOut(p.getUnauthOut());
        at.setEtcOut(p.getEtcOut());
        at.setSpecialNote(p.getSpecialNote());

        return repository.save(at);
    }
}
