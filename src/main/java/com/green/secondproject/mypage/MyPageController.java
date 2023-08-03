package com.green.secondproject.mypage;

import com.green.secondproject.mypage.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
@Tag(name = "마이페이지")
public class MyPageController {
    private final MyPageService serivce;

    @PatchMapping("/update_password")
    public int updatePassword(@RequestParam Long userId, @RequestBody String Password
                                 ,@RequestBody String newPassword) {
        UpdUserPwDto dto = new UpdUserPwDto();
        dto.setUserId(userId);
        if(Password == newPassword) {
            return serivce.updUserPw(dto);
        }
       return 0;
    }


    @PatchMapping("/teacher_info_update")
    @Operation(summary = "선생님 정보 수정",
            description = "요구값 : <br>(1)userId - 유저(선생님) PK값<br>"+
                    "(2)nm - 이름<br>(3)phone - 연락처<br>(4)address - 주소<br>")
    public int UpdTcInfo(@RequestBody UpdTeacherInfoDto dto) {
        return serivce.UpdTeacherInfo(dto);
    }


    @PatchMapping("/student_info_update")
    @Operation(summary = "학생 정보 수정",
            description = "요구값 : <br>(1)userId - 유저(학생) PK값<br>"+
                    "(2)nm - 이름<br>(3)phone - 연락처<br>(4)address - 주소<br>")
    public int UpdStdInfo(@RequestBody UpdStudentInfoDto dto) {
        return serivce.UpdStudentInfo(dto);
    }


    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "유저 프로필 사진 추가",
            description = "요구값 : <br>(1)userId - 유저 PK값<br>" +
                    "(2)pic - 사진파일<br>")
    public String patchPicUser(@RequestPart MultipartFile pic, @RequestParam Long userId) {
        UserPicDto dto = new UserPicDto();
        dto.setUserId(userId);
        return serivce.updUserPic(pic, dto);
    }


    @GetMapping("/user_mypage")
    @Operation(summary = "마이페이지",
            description = "요구값 : <br>(1)userId - 유저 PK값<br><br>" +
                    "출력값 : <br>(1)userId - 유저 PK값<br>(2)unm - 유저 이름<br>(3)email - 이메일<br>" +
                    "(4)role - 권한 [ADMIN - 관리자 / TC - 선생님 / STD - 학생]<br>" +
                    "(5)pic - 사진<br>(6)birth - 생일<br>(7)phone - 연락처<br>(8)classId - 학급 PK값<br>" +
                    "(9)grade - 학년<br>(10)van - 반<br>(11)schoolId - 학교 PK값<br>(12)schnm - 학교 이름<br>(13)address - 주소")
    public List<SelUserMyPageVo> selectTcMyPage(@RequestParam Long userId) {
        return serivce.selUserMyPage(userId);
    }
}