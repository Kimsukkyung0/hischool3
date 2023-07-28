package com.green.secondproject.myPage;

import com.green.secondproject.myPage.model.SelStudentMyPageVo;
import com.green.secondproject.myPage.model.SelTeacherMyPageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {
    private final MyPageService serivce;


    @GetMapping("/StudentMypage")
    public List<SelStudentMyPageVo> selectStdMyPage(@RequestParam int userId) {
        return serivce.selStudentMyPage(userId);
    }

    @GetMapping("/TeacherMypage")
    public List<SelTeacherMyPageVo> selectTcMyPage(@RequestParam int userId) {
        return serivce.selTeacherMyPage(userId);
    }
}
