package com.green.secondproject.teacher.subject;

import com.green.secondproject.config.security.AuthenticationFacade;
import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.teacher.subject.model.*;
import com.green.secondproject.teacher.subject.model.graph.MockGraphVo;
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


    @GetMapping("/category")
    @Operation(summary = "선생님 과목계열 리스트",
            description = "nm - 과목이름<br>" + "categoryid - 과목 계열pk값")
    List<SubjectVo> subcate() {
        return serivce.subcate();
    }


    @GetMapping()
    @Operation(summary = "선생님 세부과목 리스트 , 과목계열선택되면 그조건에 맞는것만 되도록 수정",
            description = "subjectid - 세부과목 pk값" + "<br>nm - 과목이름" + "<br> userid = 회원id"
                    + "<br> subjectid : pk값" +
                    "<br>categoryid : 과목계열 pk값")
    List<SubjectDetailVo> subject(@RequestParam Long categoryid) {
        return serivce.subject(categoryid);
    }

    @GetMapping("/category/big")
    @Operation(summary = "등록후 과목계열 리스트"
            , description =
            "출력값 : subjectid - subject 테이블에 과목 PK값" +
                    "<br>nm - 세부과목이름" +
                    "<br> userid - 유저 pk값")
    List<SubjectDetailVo2> tcslist(@AuthenticationPrincipal MyUserDetails user) {
        return serivce.tcslist(user);
    }

    @GetMapping("/category/small")
    @Operation(summary = "등록후 세부과목 리스트"
            , description =
            "categoryid - category 테이블에 세부과목 PK값" +
                    "<br>nm - 학생이름")
    List<SubjectVo2> smalllist(@AuthenticationPrincipal MyUserDetails user) {
        SubjectDto dto = new SubjectDto();
        dto.setUserid(user.getUserId());
        return serivce.smalllist(dto);
    }

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
//    }

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
        dto.setClassid(user.getClassId());
        dto.setUserid(userid);
        return serivce.stulist(dto);
    }

    //===================================Post====================================
    @PostMapping("/mock-ins")
    @Operation(summary = "모의고사 성적등록"
            , description = "subjectid - 과목 번호<br>" +
            "mon - 달<br>" +
            "standardscore - 표준점수"
            + "<br> rating - 등급"
            + "<br> percent - 백분율")
    int mockins(@RequestBody mockDto2 dto) {
        return serivce.mockins(dto);
    }

    @PostMapping
    @Operation(summary = "과목 등록",
            description = "subjectid - 과목 번호<br>")
    int instcsbj(@RequestBody SubjectInsDto2 dto) {
        return serivce.instcsbj(dto);
    }

    @PostMapping("/aca-ins")
    @Operation(summary = "학생별 내신성적등록",
            description = "subjectid - 과목 번호<br>"
                    + "<br> semester - 학기<br>"
                    + "<br>midfinal - 중간,기말(1,2)"
                    + "<br>score - 점수"
                    + "<br>rating - 등급"
                    + "<br>classrank - 반석차"
                    + "<br>woleranke - 전교석차")
    int acasubject(@RequestBody AcalistDto2 dto) {
        return serivce.acasubject(dto);
    }

    @GetMapping("/mock-graph")
    @Operation(summary = "6월 모의고사 성적조회",
    description = "nm - 과목이름<br>"+
    "rating - 등급" +
    "ratio - 등급별 인원 퍼센트(100%기준)")
    MockGraphVo mockgraph(@AuthenticationPrincipal MyUserDetails user) {
        return serivce.mockgraph(user);
    }

    @GetMapping("/aca-result")
    @Operation(summary = "내신성적 출력")
    List<ResultAcaVo> selaca(@AuthenticationPrincipal MyUserDetails user, @RequestParam Long resultId) {
        ResultAcaDto dto = new ResultAcaDto();
        dto.setUserId(user.getUserId());
        dto.setResultId(resultId);
        return serivce.selaca(dto);
    }

    @GetMapping("/mock-result")
    @Operation(summary = "모의고사 성적출력")
    List<ResultMockVo> selmock(@AuthenticationPrincipal MyUserDetails user, @RequestParam Long resultId) {

        ResultMockDto dto = new ResultMockDto();
        dto.setUserId(user.getUserId());
        dto.setResultId(resultId);
        return serivce.selmock(dto);
    }
}

