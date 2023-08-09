package com.green.secondproject.mypage;

import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.mypage.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
@Tag(name = "마이페이지")
public class MyPageController {
    private final MyPageService serivce;

//    @PatchMapping("/update-password")
//    @Operation(summary = "비밀번호 변경"
//            , description = "요구값<br><br>(1)pw - 변경할 비밀번호<br>(2)confirmpw - 비밀번호 확인")
//    public int updatePassword(@RequestBody UpdUserPwDto dto) {
//        return serivce.updUserPw(dto);
//    }


//    @PatchMapping("/teacher-info-update")
//    @Operation(summary = "선생님 정보 수정",
//            description = "(1)nm - 이름<br>(2)phone - 연락처<br>(3)address - 주소<br>(4)detailAddr - 상세 주소")
//    public int updTcInfo(@RequestBody UpdUserInfoDto dto) {
//        return serivce.updTeacherInfo(dto);
//    }

    @PatchMapping("/userr-info-update")
    @Operation(summary = "유저 정보 수정",
            description = "(1)nm - 이름<br>(2)phone - 연락처<br>(3)address - 주소<br>(4)detailAddr - 상세 주소<br>" +
                            "(5)pw - 비밀번호<br>(6)confirmPw - 비밀번호 확인")
    public int updUserInfo(@RequestBody UpdUserInfoDto dto) {
        return serivce.updUserInfo(dto);
    }



//    @PatchMapping("/student-info-update")
//    @Operation(summary = "학생 정보 수정",
//            description = "(1)nm - 이름<br>(2)phone - 연락처<br>(3)address - 주소<br>(4)detailAddr - 상세 주소")
//    public int updStdInfo(@RequestBody UpdStudentInfoDto dto) {
//        return serivce.updStudentInfo(dto);
//    }


    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "유저 프로필 사진 수정",
            description = "요구값 : <br>(1)userId - 유저 PK값<br>" +
                    "(2)pic - 사진파일<br>")
    public String patchPicUser(@RequestPart MultipartFile pic,@AuthenticationPrincipal MyUserDetails myuser) {
        return serivce.updUserPic(pic);
    }


    @GetMapping("/user-mypage")
    @Operation(summary = "마이페이지",
            description = "출력값 : <br>(1)userId - 유저 PK값<br>(2)unm - 유저 이름<br>(3)email - 이메일<br>" +
                    "(4)role - 권한 [ADMIN - 관리자 / TC - 선생님 / STD - 학생]<br>(5)pic - 사진<br>" +
                    "(6)birth - 생일<br>(7)phone - 연락처<br>(8)classId - 학급 PK값<br>(9)grade - 학년<br>" +
                    "(10)van - 반<br>(11)schoolId - 학교 PK값<br>(12)schnm - 학교 이름<br>(13)address - 주소<br>(14)detailAddr - 상세 주소")
    public List<SelUserMyPageVo> selectTcMyPage(@AuthenticationPrincipal MyUserDetails myuser) {
        return serivce.selUserMyPage(myuser);
    }


    @DeleteMapping
    @Operation(summary = "유저 탈퇴 처리",
            description = "출력값 : <br>(1)del_yn = 1 -> 삭제 처리 완료")
    public int deleteTeacher(@AuthenticationPrincipal MyUserDetails myuser) {
        return serivce.delUser(myuser);
    }
}
