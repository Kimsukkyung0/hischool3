package com.green.secondproject.common.config.security;

import com.green.secondproject.sign.model.ClassDto;
import com.green.secondproject.sign.model.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    Long selSchoolCdByNm(String nm);
}
