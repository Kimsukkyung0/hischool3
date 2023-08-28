package com.green.secondproject.career;

import com.green.secondproject.career.model.CareerDto;
import com.green.secondproject.career.model.CareerVo;
import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.entity.CareerEntity;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.repository.CareerRepository;
import com.green.secondproject.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ToString
@RequiredArgsConstructor
@Slf4j
public class CareerService {
    private final CareerRepository careerRepository;
    private final AuthenticationFacade facade;
    private final UserRepository userRepository;
    //학생 지도 (학생Select)
    public List<CareerVo> SelStu(){

        CareerDto dto = new CareerDto();
        MyUserDetails userDetails = facade.getLoginUser();

        UserEntity userEntity = userRepository.getReferenceById(userDetails.getUserId());
        dto.setUserId(userEntity.getUserId());

        UserEntity userentity = UserEntity
                .builder()
                .userId(dto.getUserId())
                .build();
        List<CareerEntity> careerList = careerRepository.findByUserEntity(userentity);
        List<CareerVo> list = new ArrayList<>();

        for (CareerEntity entity: careerList) {
            list.add(CareerVo.builder()
                    .grade(entity.getGrade())
                    .user_id(entity.getCareer_id())
                    .interest(entity.getInterest())
                            .std_hope(entity.getStd_hope())
                            .parent_hope(entity.getParent_hope())
                            .hope_univ(entity.getHope_univ())
                            .hope_dept(entity.getHope_dept())
                            .special_note(entity.getSpecial_note())
                    .build());
        }

        return list;
    }

}
