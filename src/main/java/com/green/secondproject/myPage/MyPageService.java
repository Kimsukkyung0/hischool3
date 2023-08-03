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



    public List<SelUserMyPageVo> selUserMyPage(Long userId) {
        SelUserMyPageDto dto = new SelUserMyPageDto();
        dto.setUserId(userId);
        return mapper.selUserMyPage(dto);
    }
}
