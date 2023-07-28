package com.green.secondproject.myPage;

import com.green.secondproject.myPage.model.SelStudentMyPageDto;
import com.green.secondproject.myPage.model.SelStudentMyPageVo;
import com.green.secondproject.myPage.model.SelTeacherMyPageDto;
import com.green.secondproject.myPage.model.SelTeacherMyPageVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {
    List<SelStudentMyPageVo> selStudentMyPage(SelStudentMyPageDto dto);
    List<SelTeacherMyPageVo> selTeacherMyPage(SelTeacherMyPageDto dto);
}
