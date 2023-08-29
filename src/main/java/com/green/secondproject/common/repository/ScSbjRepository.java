package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.ScSbjEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScSbjRepository extends JpaRepository<ScSbjEntity, Long> {
//    List<ScSbjEntity> saveAll(List<ScSbjInsDto2> dto);



//    public TeacherMngVoContainer teacherListOfTheSchool(Long schoolId, Pageable pageable) {
//        schoolCode = scRep.findBySchoolId(schoolId).getCode();
//        SchoolEntity scEnti = scRep.findByCode(schoolCode);
//        List<VanEntity> vanEnti = vanRep.findDistinctBySchoolEntity(scEnti);
//
//        Page<UserEntity> tcPage = userRepository.findUsersByVanEntityAndRoleType(vanEnti, RoleType.TC, pageable);
//
//        List<TeacherMngVo> subResult = tcPage.getContent().stream().map(en -> {
//            VanEntity vanEntity = vanRep.findByVanId(en.getVanEntity().getVanId());
//            return TeacherMngVo.builder()
//                    .userId(en.getUserId())
//                    .schoolNm(scEnti.getNm())
//                    .grade(vanEntity.getGrade())
//                    .vanNum(vanEntity.getClassNum())
//                    .email(en.getEmail())
//                    .nm(en.getNm())
//                    .birth(en.getBirth())
//                    .phone(en.getPhone())
//                    .address(en.getAddress())
//                    .detailAddr(en.getDetailAddr())
//                    .role(en.getRoleType().toString())
//                    .aprYn(en.getAprYn())
//                    .enrollState(en.getEnrollState())
//                    .build();
//        }).collect(Collectors.toList());

//        return TeacherMngVoContainer.builder()
//                .list(subResult)
//                .totalCount((int) tcPage.getTotalElements()) // 총 개수 설정
//                .build();
//    }
//    위 코드에서 totalCount 필드를 tcPage.getTotalElements()로 설정하여 총 개수를 가져와서 설정하고 있습니다. 이렇게 하면 클라이언트에서는 총 리스트 개수를 확인할 수 있을 것입니다.






}
