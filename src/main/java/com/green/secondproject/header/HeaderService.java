package com.green.secondproject.header;

import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.entity.SchoolAdminEntity;
import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.entity.VanEntity;
import com.green.secondproject.common.repository.SchoolAdminRepository;
import com.green.secondproject.common.repository.SchoolRepository;
import com.green.secondproject.common.repository.UserRepository;
import com.green.secondproject.common.repository.VanRepository;
import com.green.secondproject.header.model.SelSchoolInfoDto;
import com.green.secondproject.header.model.SelSchoolInfoVo;
import com.green.secondproject.header.model.SelSchoolLogoDto;
import com.green.secondproject.header.model.SelSchoolLogoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeaderService {
    private final HeaderMapper mapper;

    @Value("${file.logoPath}")
    private String logoPath;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;
    private final VanRepository vanRepository;
    private final SchoolAdminRepository schoolAdminRepository;

    public SelSchoolInfoVo selSchoolInfo(MyUserDetails myuser) {
//        SelSchoolInfoDto dto = new SelSchoolInfoDto();
//        dto.setUserId(myuser.getUserId());
//        return mapper.selSchoolInfo(dto);
//        MyUserDetails userDetails = FACADE.getLoginUser();

        SchoolEntity scEntity = schoolRepository.findBySchoolId(myuser.getSchoolId());
        if ("ROLE_ADMIN".equals(myuser.getRoles().get(0))) {
            return SelSchoolInfoVo.builder()
                    .userId(myuser.getUserId())
                    .schoolId(scEntity.getSchoolId())
                    .nm(scEntity.getNm())
                    .build();
        }
        VanEntity vanEntity = vanRepository.findByVanId(myuser.getVanId());

//        SchoolEntity scEntity = SchoolEntity.builder()
//                                            .schoolId(userDetails.getSchoolId())
//                                            .nm(userDetails.getSchoolNm())
//                                            .build();
//
//        UserEntity userEntity = UserEntity.builder()
//                                        .userId(userDetails.getUserId())
//                                        .build();
//
//        VanEntity vanEntity = VanEntity.builder()
//                                    .grade(userDetails.getGrade())
//                                    .classNum(userDetails.getClassNum())
//                                    .build();

        return SelSchoolInfoVo.builder()
                .userId(myuser.getUserId())
                .schoolId(scEntity.getSchoolId())
                .nm(scEntity.getNm())
                .grade(vanEntity.getGrade())
                .van(vanEntity.getClassNum())
                .build();
    }

    public SelSchoolLogoVo selSchoolLogo(MyUserDetails myuser) {
//        SelSchoolLogoDto dto = new SelSchoolLogoDto();
//        dto.setUserId(myuser.getUserId());
//
//        SelSchoolLogoVo logoVo = mapper.selSchoolLogo(dto);
//        logoVo.setLogo(logoPath + "/" + logoVo.getLogo());
//        return logoVo;


        /*
        자 우리가 해야할건 스쿨 로고 갖고오기다
        근데 스쿨로고는 어디있냐면 스쿨 엔티티에 있다
        추가적으로 로그인한 사람의 pk를 받고 그 사람이 속한 학교 pk를 받아와야한다 right?

        */

        SchoolEntity scEntity = schoolRepository.findBySchoolId(myuser.getSchoolId());
        scEntity.setLogo(logoPath + "/" + scEntity.getLogo());

        return SelSchoolLogoVo.builder()
                            .logo(scEntity.getLogo())
                            .build();
    }
}
