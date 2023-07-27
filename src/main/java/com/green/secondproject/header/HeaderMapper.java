package com.green.secondproject.header;

import com.green.secondproject.header.model.SelSchoolInfoDto;
import com.green.secondproject.header.model.SelSchoolInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HeaderMapper {
    List<SelSchoolInfoVo> selSchoolInfo(SelSchoolInfoDto dto);
}
