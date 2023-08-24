package com.green.secondproject.header;

import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.repository.SchoolRepository;
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
    private final AuthenticationFacade FACADE;

//    public SelSchoolInfoVo selSchoolInfo(MyUserDetails myuser) {
////        SelSchoolInfoDto dto = new SelSchoolInfoDto();
////        dto.setUserId(myuser.getUserId());
////        return mapper.selSchoolInfo(dto);
//
//
//        SchoolEntity schoolEntity = schoolRepository.getReferenceById(myuser.getSchoolId());
//        myuser.setSchoolId(schoolEntity.getSchoolId());
//        SchoolEntity entity = SchoolEntity.builder()
//                .schoolId(myuser.getSchoolId())
//                .nm(myuser.getSchoolNm())
//                .
//
//        return result;
//
//    }

    public SelSchoolLogoVo selSchoolLogo(MyUserDetails myuser) {
        SelSchoolLogoDto dto = new SelSchoolLogoDto();
        dto.setUserId(myuser.getUserId());

        SelSchoolLogoVo logoVo = mapper.selSchoolLogo(dto);
        logoVo.setLogo(logoPath + "/" + logoVo.getLogo());
        return logoVo;
    }
}
