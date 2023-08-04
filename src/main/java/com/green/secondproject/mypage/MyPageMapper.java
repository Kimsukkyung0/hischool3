package com.green.secondproject.mypage;

import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.mypage.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {
    int updTeacherInfo(UpdTeacherInfoDto2 dto);
    int updStudentInfo(UpdStudentInfoDto2 dto);
    int updUserPic(UserPicDto dto);
    int updUserPw(UpdUserPwDto dto);
    List<SelUserMyPageVo> selUserMyPage(SelUserMyPageDto dto);
}
