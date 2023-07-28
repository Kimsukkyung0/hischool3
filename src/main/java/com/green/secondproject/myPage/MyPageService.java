package com.green.secondproject.myPage;

import com.green.secondproject.myPage.model.SelStudentMyPageDto;
import com.green.secondproject.myPage.model.SelStudentMyPageVo;
import com.green.secondproject.myPage.model.SelTeacherMyPageDto;
import com.green.secondproject.myPage.model.SelTeacherMyPageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MyPageMapper mapper;


    public List<SelStudentMyPageVo> selStudentMyPage(int userId) {
        SelStudentMyPageDto dto = new SelStudentMyPageDto();
        dto.setUserId(userId);
        return mapper.selStudentMyPage(dto);
    }


    public List<SelTeacherMyPageVo> selTeacherMyPage(int userId) {
        SelTeacherMyPageDto dto = new SelTeacherMyPageDto();
        dto.setUserId(userId);
        return mapper.selTeacherMyPage(dto);
    }
}
