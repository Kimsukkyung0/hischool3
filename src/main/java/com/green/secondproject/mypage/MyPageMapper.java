package com.green.secondproject.mypage;

import com.green.secondproject.mypage.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {
    int UpdTeacherInfo(UpdTeacherInfoDto dto);
    int UpdStudentInfo(UpdStudentInfoDto dto);
    int updUserPic(UserPicDto dto);
    int updUserPw(UpdUserPwDto dto);
    List<SelUserMyPageVo> selUserMyPage(SelUserMyPageDto dto);
}
