package com.green.secondproject.myPage;

import com.green.secondproject.myPage.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {
    int UpdTeacherInfo(UpdTeacherInfoDto dto);
    int UpdStudentInfo(UpdStudentInfoDto dto);
    List<SelUserMyPageVo> selUserMyPage(SelUserMyPageDto dto);
}
