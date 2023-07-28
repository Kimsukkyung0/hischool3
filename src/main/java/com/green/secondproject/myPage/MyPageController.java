package com.green.secondproject.myPage;

import com.green.secondproject.myPage.model.SelStudentMyPageVo;
import com.green.secondproject.myPage.model.SelTeacherMyPageVo;
import com.green.secondproject.myPage.model.UpdTeacherInfoDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
@Tag(name = "마이페이지")
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
