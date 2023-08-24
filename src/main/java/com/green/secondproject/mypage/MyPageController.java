package com.green.secondproject.mypage;

import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.mypage.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
@Tag(name = "마이페이지")
public class MyPageController {
    private final MyPageService serivce;

    @GetMapping("/user-mypage")
    @Operation(summary = "마이페이지",
            description = "출력값 : <br>(1)userId - 유저 PK값<br>(2)unm - 유저 이름<br>(3)email - 이메일<br>" +
                    "(4)role - 권한 [ADMIN - 관리자 / TC - 선생님 / STD - 학생]<br>(5)pic - 사진<br>" +
                    "(6)birth - 생일<br>(7)phone - 연락처<br>(8)classId - 학급 PK값<br>(9)grade - 학년<br>" +
                    "(10)van - 반<br>(11)schoolId - 학교 PK값<br>(12)schnm - 학교 이름<br>(13)address - 주소<br>(14)detailAddr - 상세 주소")
    public SelUserMyPageVo selectMyPage(@AuthenticationPrincipal MyUserDetails myuser) {
        return serivce.selUserMyPage(myuser);
    }


    @PatchMapping
    @Operation(summary = "유저 탈퇴 처리",
            description = "출력값 : <br>(1)del_yn = 1 -> 삭제 처리 완료")
    public int delUser(@AuthenticationPrincipal MyUserDetails myuser) {
        return serivce.delUser(myuser);
    }





    @PutMapping(value = "/user-info", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "유저 정보 수정",
            description = "요구값 : <br>(1)phone - 연락처<br>(2)address - 주소<br>(3)detailAddr - 상세 주소<br>" +
                    "(4)pw - 비밀번호")
    public int updUserInfo(@RequestPart(required = false) MultipartFile pic, @RequestPart UpdInfoParam p,
                            @AuthenticationPrincipal MyUserDetails myuser) {
        return serivce.updUserInfo(pic, p, myuser);
    }

}