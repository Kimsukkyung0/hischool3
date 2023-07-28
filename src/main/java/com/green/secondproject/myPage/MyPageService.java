package com.green.secondproject.myPage;

import com.green.secondproject.myPage.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MyPageMapper mapper;

    public int updTeacherInfo(UpdTeacherInfoDto dto) {
        return mapper.updTeacherInfo(dto);
    }

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
