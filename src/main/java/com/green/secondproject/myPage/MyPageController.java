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
            description = "요구값 : <br> sdSchulCode - 학교고유코드<br> "+
                    "출력값 : <br>(1)schoolNm - 학교명<br>(2)strYearMonth - 접속한 달<br><br>"+
                    "출력값 리스트세부내역 : <br> (1)lunchOrDinner - 중식or석식 <br> (2) menuOftheDay : 식사명(한줄로표시/쉼표로 구분)")
    public int UpdTcInfo(@RequestBody UpdTeacherInfoDto dto) {
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
