package com.green.secondproject.header;

import com.green.secondproject.header.model.SelSchoolInfoDto;
import com.green.secondproject.header.model.SelSchoolInfoVo;
import com.green.secondproject.header.model.SelSchoolLogoDto;
import com.green.secondproject.header.model.SelSchoolLogoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HeaderMapper {
    SelSchoolInfoVo selSchoolInfo(SelSchoolInfoDto dto);
    SelSchoolLogoVo selSchoolLogo(SelSchoolLogoDto dto);
}
