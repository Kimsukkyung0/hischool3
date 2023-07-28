package com.green.secondproject.myPage;

import com.green.secondproject.myPage.model.SelStudentMyPageVo;
import com.green.secondproject.myPage.model.SelTeacherMyPageVo;
import com.green.secondproject.myPage.model.UpdTeacherInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {
    private final MyPageService serivce;

//    @PatchMapping("/TeacherInfoUpdate")
//    public int UpdTcInfo(@RequestParam int userId) {
//        UpdTeacherInfoDto dto = new UpdTeacherInfoDto();
//        dto.set
//        return serivce.updTeacherInfo(dto);
//    }

    @GetMapping("/StudentMypage")
    public List<SelStudentMyPageVo> selectStdMyPage(@RequestParam int userId) {
        return serivce.selStudentMyPage(userId);
    }

    @GetMapping("/TeacherMypage")
    public List<SelTeacherMyPageVo> selectTcMyPage(@RequestParam int userId) {
        return serivce.selTeacherMyPage(userId);
    }
}
