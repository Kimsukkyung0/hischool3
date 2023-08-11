package com.green.secondproject.mypage;

import com.green.secondproject.mypage.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {
    int updUserInfo(UserUpdDto dto);
    int delUser(DelUserDto dto);
    List<SelUserMyPageVo> selUserMyPage(SelUserMyPageDto dto);
}