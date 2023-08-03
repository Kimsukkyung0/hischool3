package com.green.secondproject.myPage;

import com.green.secondproject.myPage.model.SelUserMyPageVo;
import com.green.secondproject.myPage.model.UpdStudentInfoDto;
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


    @PatchMapping("/teacher_info_update")
    @Operation(summary = "선생님 정보 수정",
            description = "요구값 : <br>(1)userId - 유저(선생님) PK값<br>"+
                    "(2)nm - 이름<br>(3)phone - 연락처<br>(4)address - 주소<br>(5)사진(임시 -> 변경예정)")
    public int UpdTcInfo(@RequestBody UpdTeacherInfoDto dto) {
        return serivce.UpdTeacherInfo(dto);
    }


    @PatchMapping("/student_info_update")
    @Operation(summary = "학생 정보 수정",
            description = "요구값 : <br>(1)userId - 유저(학생) PK값<br>"+
                    "(2)nm - 이름<br>(3)phone - 연락처<br>(4)address - 주소<br>(5)사진(임시 -> 변경예정)")
    public int UpdStdInfo(@RequestBody UpdStudentInfoDto dto) {
        return serivce.UpdStudentInfo(dto);
    }




    @GetMapping("/user_mypage")
    @Operation(summary = "마이페이지",
            description = "요구값 : <br>(1)userId - 유저 PK값<br>(2)unm - 유저 이름<br>(3)email - 이메일<br>" +
                    "(4)pic - 사진(변경예정)<br>(5)birth - 생일<br>(6)phone - 연락처<br>(7)classId - 학급 PK값<br>" +
                    "(8)grade - 학년<br>(9)van - 반<br>(10)schoolId - 학교 PK값<br>(11)schnm - 학교 이름<br>(12)address - 주소")
    public List<SelUserMyPageVo> selectTcMyPage(@RequestParam Long userId) {
        return serivce.selUserMyPage(userId);
    }
}
