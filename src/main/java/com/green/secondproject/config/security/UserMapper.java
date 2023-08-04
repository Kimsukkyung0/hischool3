package com.green.secondproject.config.security;

import com.green.secondproject.config.security.model.UserEntity;
import com.green.secondproject.config.security.model.UserTokenEntity;
import com.green.secondproject.sign.model.ClassDto;
import com.green.secondproject.sign.model.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int save(UserEntity p);
    UserVo selUserByEmail(String email);
    UserVo selUserById(Long userId);

    int updUserToken(UserTokenEntity p);
    UserTokenEntity selUserToken(UserTokenEntity p);

    Long selSchoolCdByNm(String nm);
    Long selSchoolIdByNm(String nm);
    Long selClassId(ClassDto dto);
}
