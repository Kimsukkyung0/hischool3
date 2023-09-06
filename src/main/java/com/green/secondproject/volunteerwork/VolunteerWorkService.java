package com.green.secondproject.volunteerwork;

import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.entity.VolunteerWorkEntity;
import com.green.secondproject.common.repository.UserRepository;
import com.green.secondproject.common.repository.VolunteerWorkRepository;
import com.green.secondproject.volunteerwork.model.VolWorkInsDto;
import com.green.secondproject.volunteerwork.model.VolWorkVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class VolunteerWorkService {
    private final VolunteerWorkRepository volRep;
    private final UserRepository usrRep;
    private final AuthenticationFacade facade;

    public VolWorkVo postVolunteerWork(VolWorkInsDto dto){
        UserEntity usrEnti = usrRep.findByUserId(dto.getUserId());
        if(facade.getLoginUser().getSchoolId() == usrEnti.getVanEntity().getSchoolEntity().getSchoolId()
        && usrEnti.getRoleType().equals(RoleType.STD)) {
//            List<VolunteerWorkEntity> volEnti = volRep.findAllByUserEntityAndGrade(usrEnti,grade);
            int totalHrs = volRep.findTotalHrsByUserEntity(usrEnti);
            VolunteerWorkEntity postUsrVolRec = volRep.save(VolunteerWorkEntity.builder()
                    .userEntity(usrEnti)
                    .grade(dto.getGrade())
                    .startDate(dto.getStartDate())
                            .endDate(dto.getEndDate())
                            .place(dto.getPlace())
                            .ctnt(dto.getCtnt())
                            .hrs(dto.getHrs())
                            .totalHrs(dto.getHrs()+totalHrs)
                    .build());
            return VolWorkVo.builder()
                    .volunteerId(postUsrVolRec.getVolunteerId())
                    .userId(dto.getUserId())
                    .grade(postUsrVolRec.getGrade())
                    .startDate(postUsrVolRec.getStartDate())
                    .endDate(postUsrVolRec.getEndDate())
                    .place(postUsrVolRec.getPlace())
                    .ctnt(postUsrVolRec.getCtnt())
                    .hrs(postUsrVolRec.getHrs())
                    .totalHrs(postUsrVolRec.getTotalHrs())
                    .build();
        }else{
            throw new RuntimeException("올바른 요청값이 아닙니다");
        }
    }
}
