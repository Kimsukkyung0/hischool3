package com.green.secondproject.common.config.security;

import com.green.secondproject.sign.model.ClassDto;
import com.green.secondproject.sign.model.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    //int save(UserEntity p);
    UserVo selUserByEmail(String email);
    UserVo selUserById(Long userId);

    Long selSchoolCdByNm(String nm);
    Long selSchoolIdByNm(String nm);
    Long selClassId(ClassDto dto);
}
