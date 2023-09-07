//package com.green.secondproject.awards;
//
//import com.green.secondproject.awards.model.AwardsInsDto;
//import com.green.secondproject.awards.model.AwardsVo;
//import com.green.secondproject.common.config.security.AuthenticationFacade;
//import com.green.secondproject.common.config.security.model.RoleType;
//import com.green.secondproject.common.entity.AwardsEntity;
//import com.green.secondproject.common.entity.UserEntity;
//import com.green.secondproject.common.repository.AwardsRepository;
//import com.green.secondproject.common.repository.UserRepository;
//import com.green.secondproject.volunteerwork.model.VolWorkInsDto;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//@AllArgsConstructor
//public class AwardsService {
//    private final UserRepository usrRep;
//    private final AuthenticationFacade facade;
//    private final AwardsRepository awsRep;
//
//    .AwardsService{
//        UserEntity usrEnti = usrRep.findByUserId(dto.getUserId());
//        if(facade.getLoginUser().getSchoolId() == usrEnti.getVanEntity().getSchoolEntity().getSchoolId()
//                && usrEnti.getRoleType().equals(RoleType.STD)) {
//
//               AwardsEntity awsEnti =  awsRep.save(AwardsEntity.builder().
//                        userEntity(usrEnti)
//                        .nm(dto.getNm())
//                        .prize(dto.getPrize())
//                        .awardedAt(dto.getAwardedAt())
//                        .awardedBy(dto.getAwardedBy())
//                        .participation(dto.getParticipation())
//                        .build());
//
//               return AwardsVo.builder()
//                       .awardId(awsEnti.getAwardId())
//                       .userId(dto.getUserId())
//                       .nm(awsEnti.getNm())
//                       .prize(awsEnti.getPrize())
//                       .awardedAt(awsEnti.getAwardedAt())
//                       .awardedBy(awsEnti.getAwardedBy())
//                       .participation(awsEnti.getParticipation())
//                       .build();
//        }
//        else{
//            throw new RuntimeException("올바른 요청값이 아닙니다");
//        }
//    }
//}
//
