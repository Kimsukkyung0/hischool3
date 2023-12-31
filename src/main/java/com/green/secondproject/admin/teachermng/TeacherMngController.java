package com.green.secondproject.admin.teachermng;


import com.green.secondproject.admin.teachermng.model.*;
import com.green.secondproject.common.config.etc.EnrollState;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/admin/tc")
@RequiredArgsConstructor
@Tag(name = "관리자-교원관리")
public class TeacherMngController {
        private final TeacherMngService service;

    @GetMapping("/{userId}")
    @Operation(summary = "승인처리용 교원신청데이터", description = "요구값 : <br> (1)userId : 조회대상선생님pk <br><br> 출력값 : <br> (1)userId : 유저pk<br>(2)grade : 담당학년 <br> (3)vanNum : 담당학반 <br>(4)email : 교원email <br> (5)nm : 교원이름<br> (6)birth : 생년월일<br>(7)phone :교원연락처<br>"+
            "(8)address : 상위주소<br> (9)detailAddr : 상세주소 <br> (10)role : 권한명(TC : 선생님) <br> (11)aprYn : 승인여부(0:미승인)"+
            "(12)enrollState : 재직상태(ENROLL : 재직중)<br>(13)aprPic : 이미지주소 <br>(ex:http://192.168.0.144:5003/img/hiSchool/userApr3a75053c5-b0f7-4537-8d3f-cbcd270e420a.jpg)")
    TeacherMngWithPicVo teacherDetailNotApr(@PathVariable Long userId){
        return service.teacherDetailNotApr(userId);
    }

    @GetMapping
    @Operation(summary = "각 학교의 승인대기중 교원목록", description = "요구값 : <br>(1)page : 페이지수(default : 1 )<br> (2)size : 한페이지당 보여줄 게시물 수 <br>" +
            "(3)sort : 정렬기준 컬럼(default : 승인신청일자,이름 오름차순  / 방법 : 빈 쌍따옴표로 조회 : \"\" )<br><br>"+
            "출력값 : <br><br> (1)userId : 유저pk<br>(2)classId : 반 코드 <br> (3)email : 교원email <br> (4)nm : 교원이름<br> (5)birth : 생년월일<br>(6)phone :교원연락처<br>"+
            "(7)address : 상위주소<br> (8)detailAddr : 상세주소 <br> (9)role : 권한명(TC : 선생님) <br> (10)aprYn : 승인여부(0:미승인)"+
            "(11)enrollState : 재직상태(ENROLL : 재직중)<br> (12)totalCount : 총 교원수<br> (13)totalPage : 총 페이지 수 \"")
    ResponseEntity<TeacherMngVoContainer> teacherNotapprovedListTmp(Pageable page){
        return ResponseEntity.ok(service.teacherNotapprovedList(page));
    }

    @GetMapping("/all")
    @Operation(summary = "각 학교의 전체 교원목록", description = """
            요구값 : <br>(1)page(선택) : 페이지수(default : 1 )<br> 
                       (2)search(선택) : 검색어 (이름 검색)<br>(3)enrollState(선택) : 상태값정렬 <br><br>
            출력값 : <br>(1)userId : 유저pk<br>(2)classId : 반 코드 <br> (3)email : 교원email <br> (4)nm : 교원이름<br> (5)birth : 생년월일<br>(6)phone :교원연락처<br>
            (7)address : 상위주소<br> (8)detailAddr : 상세주소 <br> (9)role : 권한명(TC : 선생님) <br> (10)aprYn : 승인여부(0:미승인)
            (11)enrollState : 재직상태<br>
            (ENROLL : 재직중 / LEAVE : 탈퇴 / TRANSFER : 전근)<br>
            (12)totalCount : 총 교원수<br> (13)totalPage : 총 페이지 수 """)
    ResponseEntity<TeacherMngVoContainer> allTeachersOfTheSchool(Optional<TeacherListDto> dto){
        if(dto.isPresent()){
        log.info("dto get:{}",dto.get());}
        return ResponseEntity.ok(service.teacherListOfTheSchool(dto));
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
    public String setAprYnOnTeacherAcnt(@AuthenticationPrincipal MyUserDetails myuser, @RequestParam Long userId){
        return service.teacherAprv(userId ,myuser.getSchoolId());
    }
    @GetMapping("/{year}/{grade}")
    @Operation(summary = "학반정보" , description = """
            요구값 : <br>(1) year : 변경대상연도<br> (2) grade : 변경대상학년<br><br>
            출력값 : <br>(1)List<number> : api에 존재하는 해당연도/학년에 속해있는 학반 값(ex) 1,2,3,4,...) <br>
            <연도값의 경우 올해/혹은 후년만 해당되는지 검사합니다><br>
            <학반 값이 api 에 등록되어있지 않은경우 '해당 학반 값 없음'메세지가 출력됩니다.""")
    public List<Integer> getClassListForTeacher(@PathVariable int grade, @PathVariable int year){
        return service.getClassListForTeacher(grade,year);
    }
    @GetMapping("/stat")
    @Operation(summary = "선생님재직여부변경리스트" , description = """
            요구값 : 없음<br><br>
            출력값 : <br>(1)ENROLL : 재직  <br>(2)GRADUATION : 퇴직 <br> (3)TRANSFER : 전근 <br>(3)LEAVE : 휴직 <br>""")
    public List<String> getTeacherStatList(){
        return service.getTeacherStatList();
    }

//    @PatchMapping
//    public ResponseEntity<TeacherMngVo> updTeacherStatusAndVan(@RequestParam Long userId){ //TeacherStatUpdDto dto
////        log.info("dto : {}",dto);
//        log.info("userId : {}",userId);
//        TeacherMngVo vo = service.teacherStatUpd(dto);
//        return ResponseEntity.ok(vo);
//    }

    @PatchMapping
    @Operation(summary = "선생님재직여부/학반 변경" , description = """
            요구값 : <br>(1)userId : 변경대상유저 pk <br>(2)enrollState : 변경<br>(3)year : 변경대상연도<br>
            (4)grade : 변경학년(0일경우 해당없음으로 변경)<br>(5)classNum : 변경대상학반<br><br>
            출력값 : <br> <br> (1)userId : 변경대상선생님pk<br> (2)schoolNm : 학교이름 <br>(3)grade : 변경된 학년 <br> 
            (4)vanNum : 변경된 학반 <br>(5)email : 교원email <br> (6)nm : 교원이름<br> (7)birth : 생년월일<br>(8)phone :교원연락처<br>"+
            "(9)address : 상위주소<br> (10)detailAddr : 상세주소 <br> (11)role : 권한명(TC : 선생님) <br> (12)aprYn : 승인여부(0:미승인)"+
            "(13)enrollState : 변경된 재직상태(ENROLL : 재직중)<br>""")
    public ResponseEntity<TeacherMngVo> updTeacherStatusAndVan(@RequestBody TeacherStatUpdDto dto){
    //        log.info("dto : {}",dto);
        log.info("TeacherStatUpdDto dto : {}",dto.getUserId());
        TeacherMngVo vo = service.teacherStatUpd(dto);
        return ResponseEntity.ok(vo);
    }

}
