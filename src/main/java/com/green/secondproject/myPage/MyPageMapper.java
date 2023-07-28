package com.green.secondproject.myPage;

import com.green.secondproject.myPage.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {
//    int updTeacherInfo(int userId);
    List<SelStudentMyPageVo> selStudentMyPage(SelStudentMyPageDto dto);
    List<SelTeacherMyPageVo> selTeacherMyPage(SelTeacherMyPageDto dto);
}
