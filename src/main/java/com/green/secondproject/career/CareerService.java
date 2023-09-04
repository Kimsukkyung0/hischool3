package com.green.secondproject.career;

import com.green.secondproject.career.model.*;
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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
                    .userId(userentity.getUserId())
                    .grade(entity.getGrade())
                    .careerId(entity.getCareer_id())
                    .interest(entity.getInterest())
                    .stdHope(entity.getStd_hope())
                    .parentHope(entity.getParent_hope())
                    .hopeUniv(entity.getHope_univ())
                    .hopeDept(entity.getHope_dept())
                    .specialNote(entity.getSpecial_note())
                    .build());
        }
        return list;
    }
    public List<CareerVo> SelByStu(CareerSelByDto dto){


        UserEntity userentity = userRepository.getReferenceById(dto.getUserId());
        List<CareerEntity> careerEntityList = careerRepository.findByUserEntity(userentity);


        List<CareerVo> list = new ArrayList<>();
        for (CareerEntity entity: careerEntityList) {
            list.add(CareerVo.builder()
                    .grade(entity.getGrade())
                    .careerId(entity.getCareer_id())
                    .stdHope(entity.getStd_hope())
                            .interest(entity.getInterest())
                            .userId(userentity.getUserId())
                            .hopeDept(entity.getHope_dept())
                            .hopeUniv(entity.getHope_univ())
                            .parentHope(entity.getParent_hope())
                            .specialNote(entity.getSpecial_note())
                            .userId(entity.getUserEntity().getUserId())
                    .build());

        }
        return list;
    }

    public List<CareerVo3> HopeSel(CareerDto dto){
        UserEntity userEntity = userRepository.findByUserId(dto.getUserId());
        List<CareerEntity> careerEntity = careerRepository.findByUserEntity(userEntity);
        List<CareerVo3> list = new ArrayList<>();
        if (careerEntity.isEmpty()) {
            return null; // 혹은 적절한 기본값 또는 예외 처리
        }
        careerEntity.sort(Comparator.comparing(CareerEntity::getCreatedAt).reversed());
        CareerEntity mostRecent = careerEntity.get(0);

        CareerVo3 mostRecentVo = CareerVo3.builder()
                .hopeUniv(mostRecent.getHope_univ())
                .careerId(mostRecent.getCareer_id())
                .hopeDept(mostRecent.getHope_dept())
                .build();
        list.add(mostRecentVo);
//
//        for (CareerEntity e : careerEntity) {
//            list.add(CareerVo3.builder()
//                    .hopeUniv(e.getHope_univ())
//                    .careerId(e.getCareer_id())
//                    .hopeDept(e.getHope_dept())
//                    .build());
//        }
        return list;
    }

    public CareerVo StuIns(CareerInsDto dto){
        UserEntity userEntity = userRepository.getReferenceById(dto.getUserId());

        CareerEntity careerEntity = CareerEntity.builder()
                .std_hope(dto.getStdHope())
                .parent_hope(dto.getParentHope())
                .userEntity(userEntity)
                .grade(dto.getGrade())
                .interest(dto.getInterest())
                .hope_univ(dto.getHopeUniv())
                .hope_dept(dto.getHopeDept())
                .special_note(dto.getSpecialNote())
                .build();
            CareerEntity result = careerRepository.save(careerEntity);

            return  CareerVo.builder()
                .stdHope(result.getStd_hope())
                .careerId(result.getCareer_id())
                .parentHope(result.getParent_hope())
                .userId(userEntity.getUserId())
                .grade(result.getGrade())
                .interest(result.getInterest())
                .hopeUniv(result.getHope_univ())
                .hopeDept(result.getHope_dept())
                .specialNote(result.getSpecial_note())
                .build();
    }
    public CareerVo2 CareerUp(CareerUpDto dto){

//        CareerEntity careerEntity =
//                CareerEntity.builder()
//                        .std_hope(dto.getStdHope())
//                        .parent_hope(dto.getParentHope())
//                        .userEntity(userEntity)
//                        .grade(dto.getGrade())
//                        .interest(dto.getInterest())
//                        .hope_univ(dto.getHopeUniv())
//                        .hope_dept(dto.getHopeDept())
//                        .special_note(dto.getSpecialNote())
//                        .build();
//        CareerEntity result = careerRepository.save(careerEntity);

        Optional<CareerEntity> career = careerRepository.findById(dto.getCareerId());
        CareerEntity entity = career.get();
        entity.setCareer_id(dto.getCareerId());
        entity.setGrade(dto.getGrade());
        entity.setInterest(dto.getInterest());
        entity.setHope_dept(dto.getHopeDept());
        entity.setParent_hope(dto.getParentHope());
        entity.setSpecial_note(dto.getSpecialNote());
        entity.setStd_hope(dto.getStdHope());
        entity.setHope_univ(dto.getHopeUniv());

careerRepository.save(entity);
        return   CareerVo2.builder()
                .careerId(entity.getCareer_id())
                .stdHope(entity.getStd_hope())
                .parentHope(entity.getParent_hope())
                .grade(entity.getGrade())
                .interest(entity.getInterest())
                .hopeUniv(entity.getHope_univ())
                .hopeDept(entity.getHope_dept())
                .specialNote(entity.getSpecial_note())
                .build();
    }

}
