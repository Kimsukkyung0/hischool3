package com.green.secondproject.mypage;

import com.green.secondproject.mypage.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {
    int updTeacherInfo(UpdUserInfoDto2 dto2);
    int updStudentInfo(UpdStudentInfoDto2 dto2);
    int updUserInfo(UpdUserInfoDto2 dto2);
    int updUserPic(UserPicDto2 dto2);
    int updUserPw(UpdUserPwDto2 dto2);
    List<SelUserMyPageVo> selUserMyPage(SelUserMyPageDto dto);
}
