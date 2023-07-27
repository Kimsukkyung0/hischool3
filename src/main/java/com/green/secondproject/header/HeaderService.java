package com.green.secondproject.header;

import com.green.secondproject.header.model.SelSchoolInfoDto;
import com.green.secondproject.header.model.SelSchoolInfoVo;
import com.green.secondproject.header.model.SelSchoolLogoDto;
import com.green.secondproject.header.model.SelSchoolLogoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeaderService {
    private final HeaderMapper mapper;


    public List<SelSchoolInfoVo> selSchoolInfo(int userId) {
        SelSchoolInfoDto dto = new SelSchoolInfoDto();
        dto.setUserId(userId);
        return mapper.selSchoolInfo(dto);
    }

    public List<SelSchoolLogoVo> selSchoolLogo(int userId) {
        SelSchoolLogoDto dto = new SelSchoolLogoDto();
        dto.setUserId(userId);
        return mapper.selSchoolLogo(dto);
    }
}
