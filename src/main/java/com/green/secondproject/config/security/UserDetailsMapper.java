package com.green.secondproject.config.security;

import com.green.secondproject.config.security.model.UserEntity;
import com.green.secondproject.config.security.model.UserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDetailsMapper {
    int save(UserEntity p);
    UserEntity getByUid(String uid);


    int updUserToken(UserTokenEntity p);
    UserTokenEntity selUserToken(UserTokenEntity p);
}
