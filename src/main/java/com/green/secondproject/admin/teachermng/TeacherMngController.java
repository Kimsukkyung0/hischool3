package com.green.secondproject.admin.teachermng;


import com.green.secondproject.admin.teachermng.model.TeacherMngVo;
import com.green.secondproject.admin.teachermng.model.TeacherMngVoContainer;
import com.green.secondproject.admin.teachermng.model.TeacherMngWithPicVo;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin/tc")
@RequiredArgsConstructor
@Tag(name = "관리자-교원관리")
public class TeacherMngController {
        private final TeacherMngService teacherMngService;

    @GetMapping("/{userId}")
    @Operation(summary = "승인처리용 교원신청데이터(잘 작동합니다)", description = "요구값 : <br> (1)userId : 조회대상선생님pk <br><br> 출력값 : <br> (1)userId : 유저pk<br>(2)grade : 담당학년 <br> (3)vanNum : 담당학반 <br>(4)email : 교원email <br> (5)nm : 교원이름<br> (6)birth : 생년월일<br>(7)phone :교원연락처<br>"+
            "(8)address : 상위주소<br> (9)detailAddr : 상세주소 <br> (10)role : 권한명(TC : 선생님) <br> (11)aprYn : 승인여부(0:미승인)"+
            "(12)enrollState : 재직상태(ENROLL : 재직중)<br>(13)aprPic : 이미지주소 <br>(ex:http://192.168.0.144:5003/img/hiSchool/userApr3a75053c5-b0f7-4537-8d3f-cbcd270e420a.jpg)")
    TeacherMngWithPicVo teacherDetailNotApr(@PathVariable Long userId){
        return teacherMngService.teacherDetailNotApr(userId);
    }

    @GetMapping("/1/{userId}")
    @Operation(summary = "교원디테일2-예외처리중/수정중")
    TeacherMngWithPicVo teacherDetailNotApr2(@AuthenticationPrincipal MyUserDetails myuser, @PathVariable @RequestParam Long userId){
        return teacherMngService.teacherDetailNotApr2(myuser.getUserId(),userId);
    }

    @GetMapping
    @Operation(summary = "각 학교의 승인대기중 교원목록", description = "요구값 : <br>(1)page : 페이지수(default : 1 )<br> (2)size : 한페이지당 보여줄 게시물 수 <br>" +
            "(3)sort : 정렬기준 컬럼(default : 승인신청일자,이름 오름차순  / 방법 : 빈 쌍따옴표로 조회 : \"\" )<br><br>"+
            "출력값 : <br><br> (1)userId : 유저pk<br>(2)classId : 반 코드 <br> (3)email : 교원email <br> (4)nm : 교원이름<br> (5)birth : 생년월일<br>(6)phone :교원연락처<br>"+
            "(7)address : 상위주소<br> (8)detailAddr : 상세주소 <br> (9)role : 권한명(TC : 선생님) <br> (10)aprYn : 승인여부(0:미승인)"+
            "(11)enrollState : 재직상태(ENROLL : 재직중)<br> (12)totalCount : 총 교원수<br> (13)totalPage : 총 페이지 수 \"")
    ResponseEntity<TeacherMngVoContainer> teacherNotapprovedListTmp(@AuthenticationPrincipal MyUserDetails myuser, Pageable page){
        return ResponseEntity.ok(teacherMngService.teacherNotapprovedList(myuser.getSchoolId(),page));
    }

    @GetMapping("/all")
    @Operation(summary = "각 학교의 전체 교원목록", description = "요구값 : <br>(1)page : 페이지수(default : 1 )<br> (2)size : 한페이지당 보여줄 게시물 수 <br>" +
            "(3)sort : 정렬기준 컬럼(default : 이름 오름차순 / 방법 : 빈 쌍따옴표로 조회 : \"\" )<br><br>"+
            "출력값 : <br><br> (1)userId : 유저pk<br>(2)classId : 반 코드 <br> (3)email : 교원email <br> (4)nm : 교원이름<br> (5)birth : 생년월일<br>(6)phone :교원연락처<br>"+
            "(7)address : 상위주소<br> (8)detailAddr : 상세주소 <br> (9)role : 권한명(TC : 선생님) <br> (10)aprYn : 승인여부(0:미승인)"+
            "(11)enrollState : 재직상태<br>" +
            "(ENROLL : 재직중 / LEAVE : 탈퇴 / TRANSFER : 전근)<br>" +
            "(12)totalCount : 총 교원수<br> (13)totalPage : 총 페이지 수 ")
    ResponseEntity<TeacherMngVoContainer> allTeachersOfTheSchool(@AuthenticationPrincipal MyUserDetails myuser,Pageable page){

        return ResponseEntity.ok(teacherMngService.teacherListOfTheSchool(myuser.getSchoolId(),page));
    }


    @PutMapping
    @Operation(summary = "담당교원 승인처리" , description = """
            요구값 : <br>(1)userId : 승인처리대상 선생님 pk<br><br>
            출력값 : <br>(1) 승인처리되었습니다 - <br>
            (2) aprYn - (0)이외의 값으로 저장되어있는 경우 1은 이미 승인처리된 유저입니다<br>
            (3) 올바른 요청이 아닙니다 : 같은 학교소속이지만 승인대상이 아닌경우 <br>
            (4) 권한이없는 유저에 대한 요청 : 같은 학교 소속이 아닌 유저를 승인처리 시도 <br>
            (5) 존재하지 않는 유저입니다 : 존재하지 않는 유저를 승인처리 시도 <br>
            """)
    String setAprYnOnTeacherAcnt(@AuthenticationPrincipal MyUserDetails myuser, @RequestParam Long userId){
        return teacherMngService.teacherAprv(userId ,myuser.getSchoolId());
    }
//    findUsersByVanEntityAndRoleType
}
