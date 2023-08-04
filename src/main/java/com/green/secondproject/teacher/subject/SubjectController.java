package com.green.secondproject.teacher.subject;

import com.green.secondproject.config.security.AuthenticationFacade;
import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.teacher.subject.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ToString
@RequestMapping("/api/teacher/subject")
@Tag(name = "과목")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectSerivce serivce;
    private final AuthenticationFacade facade;


    @GetMapping("/category")
    @Operation(summary = "선생님 과목계열 리스트",
            description = "nm - 학생이름")
    List<SubjectVo> subcate(){
        return serivce.subcate();
    }



    @GetMapping()
    @Operation(summary = "선생님 세부과목 리스트 , 과목계열선택되면 그조건에 맞는것만 되도록 수정" ,
            description =   "<br>nm - 학생이름")
    List<SubjectDetailVo> subject(@RequestParam Long categoryid){
        return serivce.subject(categoryid);
    }

    @GetMapping("/category/big")
    @Operation(summary = "등록후 과목계열 리스트"
            ,description =
            "출력값 : subjectid - subject 테이블에 과목 PK값" +
                    "<br>nm - 학생이름")
    List<SubjectDetailVo2> tcslist(@AuthenticationPrincipal MyUserDetails user)
    {

        return serivce.tcslist(user);
    }

    @GetMapping("/category/small")
    @Operation(summary = "등록후 세부과목 리스트"
            ,description =
            "categoryid - category 테이블에 세부과목 PK값" +
            "<br>nm - 학생이름")
    List<SubjectVo2> smalllist(@AuthenticationPrincipal MyUserDetails user){

        return serivce.smalllist(user);
    }

    @GetMapping("/class-num")
    @Operation(summary = "반석차 반전체인원")
    int classnum(@AuthenticationPrincipal MyUserDetails user){

        return serivce.classnum(user);
    }

    @GetMapping("/school-snum")
    @Operation(summary = "학교 학년전체인원")
    int schoolnum(@AuthenticationPrincipal MyUserDetails user){
        return serivce.schoolnum(user);
    }


    //모의고사 시작

    @GetMapping("/mockbig-list")
    @Operation(summary = "모의고사 계열선택List")
    List<MockSubjectBigVo> mockbiglist(){
        return serivce.mockbiglist();
    }

    @GetMapping("/mocksmall-list")
    @Operation(summary = "모의고사 세부과목선택List 과목계열선택되면 그조건에 맞는것만 되도록 수정")
    List<MockSubjcetSmallVo> mocksmalllist(Long categoryid){
        return serivce.mocksmalllist(categoryid);
    }




    //===================================Post====================================
    @PostMapping("/mock-ins")
    @Operation(summary = "모의고사 성적등록")
    int mockins(@RequestBody mockDto2 dto,@AuthenticationPrincipal MyUserDetails user){
        return serivce.mockins(dto);
    }

    @PostMapping
    @Operation(summary = "과목 등록",
            description =     "subjectid - 과목 번호<br>")
    int instcsbj(@RequestBody SubjectInsDto dto){
        return serivce.instcsbj(dto);
    }

    @PostMapping("/aca-ins")
    @Operation(summary = "학생별 내신성적등록")
    int acasubject(@RequestBody AcalistDto dto){
        return serivce.acasubject(dto);
    }

}

