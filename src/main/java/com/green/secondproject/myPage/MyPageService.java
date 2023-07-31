package com.green.secondproject.myPage;

import com.green.secondproject.myPage.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MyPageMapper mapper;

    public int UpdTeacherInfo(UpdTeacherInfoDto dto) {
        return mapper.UpdTeacherInfo(dto);
    }


    public int UpdStudentInfo(UpdStudentInfoDto dto) {
        return mapper.UpdStudentInfo(dto);
    }


    public List<SelStudentMyPageVo> selStudentMyPage(Long userId) {
        SelStudentMyPageDto dto = new SelStudentMyPageDto();
        dto.setUserId(userId);
        return mapper.selStudentMyPage(dto);
    }


    public List<SelTeacherMyPageVo> selTeacherMyPage(Long userId) {
        SelTeacherMyPageDto dto = new SelTeacherMyPageDto();
        dto.setUserId(userId);
        return mapper.selTeacherMyPage(dto);
    }
}
