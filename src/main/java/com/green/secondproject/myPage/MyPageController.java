package com.green.secondproject.myPage;

import com.green.secondproject.myPage.model.SelStudentMyPageVo;
import com.green.secondproject.myPage.model.SelTeacherMyPageVo;
import com.green.secondproject.myPage.model.UpdTeacherInfoDto;
import io.swagger.v3.oas.annotations.Operation;
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

    @PatchMapping("/TeacherInfoUpdate")
    @Operation(summary = "선생님 정보 수정",
            description = "요구값 : <br> userId - 유저(선생님) PK값<br> "+
                    "출력값 : <br>(1)strYearMonth - 접속한 달<br>"+
                    "<br>(2)nm - 이름<br>(3)phone - 연락처<br>(4)address - 주소<br>(5)사진(임시 -> 변경예정)")
    public int UpdTcInfo(@RequestBody UpdTeacherInfoDto dto) {
        return serivce.updTeacherInfo(dto);
    }


    @PatchMapping("/TeacherInfoUpdate")
    @Operation(summary = "학생 정보 수정",
            description = "요구값 : <br> userId - 유저(학생) PK값<br> "+
                    "출력값 : <br>(1)strYearMonth - 접속한 달<br>"+
                    "<br>(2)nm - 이름<br>(3)phone - 연락처<br>(4)address - 주소<br>(5)사진(임시 -> 변경예정)")
    public int UpdStdInfo(@RequestBody UpdTeacherInfoDto dto) {
        return serivce.updTeacherInfo(dto);
    }


    @GetMapping("/StudentMypage")
    @Operation(summary = "선생님 정보 수정",
            description = "요구값 : <br> userId - 학생 PK값<br> "+
                    "출력값 : <br>(1)userId - 학생 PK값<br>(2)stdnm - 학생 이름<br>(3)email - 이메일<br>" +
                    "(4)pic - ")
    public List<SelStudentMyPageVo> selectStdMyPage(@RequestParam int userId) {
        return serivce.selStudentMyPage(userId);
    }

    @GetMapping("/TeacherMypage")
    public List<SelTeacherMyPageVo> selectTcMyPage(@RequestParam int userId) {
        return serivce.selTeacherMyPage(userId);
    }
}
