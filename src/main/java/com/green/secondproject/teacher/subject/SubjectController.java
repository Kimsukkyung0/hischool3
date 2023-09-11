package com.green.secondproject.teacher.subject;

import com.green.secondproject.admin.schoolsubject.ScSbjService;
import com.green.secondproject.teacher.subject.model.graph.MockGraphVo;
import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.teacher.subject.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ToString
@RequestMapping("/api/teacher/subject")
@Tag(name = "과목")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService serivce;
    private final AuthenticationFacade facade;
    private final ScSbjService scSbjService;

    //모의고사 시작

    @GetMapping("/mockbig-list")
    @Operation(summary = "모의고사 계열선택List", description = "categoryid - 과목 계열pk값<br>" + "nm - 과목계열 이름")
    List<MockSubjectBigVo> mockbiglist() {
        return serivce.mockbiglist();
    }

    @GetMapping("/mocksmall-list")
    @Operation(summary = "모의고사 세부과목선택List", description = "입력값 : categoryid - 과목계열<br>"
            + "subjectid - 세부과목pk<br>" + "nm - 세부과목명<br>" + "categoryid - 과목 계열pk값")
    List<MockSubjcetSmallVo> mocksmalllist(Long categoryid) {
        return serivce.mocksmalllist(categoryid);
    }

    @GetMapping("/stulist")
    @Operation(summary = "학생pk값과 이름")
    List<StudentListVo> stulist(@AuthenticationPrincipal MyUserDetails user,@RequestParam Long userid) {
        StudentListDto dto = new StudentListDto();
        dto.setVanId(user.getVanId());
        dto.setUserid(userid);
        return serivce.stulist(dto);
    }

    @GetMapping("/aca-category")
    @Operation(summary = "담당 학년 내신 과목계열 조회")
    public List<AcaCategoryVo> getAcaCategory() {
        return serivce.selAcaCategory();
    }

    @GetMapping("/aca-subject")
    @Operation(summary = "선택한 계열 세부과목 조회")
    public List<AcaResultSubVo> getAcaSubject(Long categoryId) {
        return serivce.selAcaSubject(categoryId);
    }

    //==================================3차 전환============== OR =====================

    //====================================관리자 과목권한 이전으로 삭된부분

//    @GetMapping("/category/big")
//    @Operation(summary = "등록후 과목계열 리스트"제
//            , description =
//            """
//                    출력값 : subjectid - subject 테이블에 과목 PK값
//                    "<br>nm - 세부과목이름""")
//    List<SubjectVo> tcslist() {
//        return serivce.tcslist();
//    }
//
//    @GetMapping("/category/small")
//    @Operation(summary = "등록후 세부과목 리스트"
//            , description =
//            "categoryid - category 테이블에 세부과목 PK값" +
//                    "<br>nm - 학생이름")
//    List<SubjectVo3> smalllist(@RequestParam Long categoryId) {
//        return serivce.smallList(categoryId);
//    }

//    @GetMapping("/class-num")
//    @Operation(summary = "반석차 반전체인원")
//    int classnum(@AuthenticationPrincipal MyUserDetails user){
//
//        return serivce.classnum(user);
//    }
//
//    @GetMapping("/school-snum")
//    @Operation(summary = "학교 학년전체인원")
//    int schoolnum(@AuthenticationPrincipal MyUserDetails user){
//        return serivce.schoolnum(user);


//    @GetMapping("/category")
//    @Operation(summary = "선생님 과목계열 리스트",
//            description = "nm - 과목이름<br>" + "categoryid - 과목 계열pk값")
//    List<SubjectVo> subcate() {
//        return serivce.subcate();
//    }
//
//    @GetMapping()
//    @Operation(summary = "선생님 세부과목 리스트 , 과목계열선택되면 그조건에 맞는것만 되도록 수정",
//            description = "subjectid - 세부과목 pk값" + "<br>nm - 과목이름" + "<br> userid = 회원id"
//                    + "<br> subjectid : pk값" +
//                    "<br>categoryid : 과목계열 pk값")
//    List<SubjectDetailVo> subject(@RequestParam Long categoryid) {
//        return serivce.subject(categoryid);
//    }
//    }
//
//    @PostMapping
//    @Operation(summary = "과목 등록",
//            description = "subjectid - 과목 번호<br>")
//    int instcsbj(@RequestBody SubjectInsDto2 dto) {
//        return serivce.instcsbj(dto);
//    }



}

