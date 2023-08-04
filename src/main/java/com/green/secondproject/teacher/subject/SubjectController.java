package com.green.secondproject.teacher.subject;

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


    @GetMapping("/category")
    @Operation(summary = "선생님 과목계열 리스트",
            description = "nm - 학생이름"+
                    "<br>categoryid - category 테이블에 카테고리pk값")
    List<SubjectVo> subcate(){
        return serivce.subcate();
    }

    @GetMapping()
    @Operation(summary = "선생님 세부과목 리스트 , 과목계열선택되면 그조건에 맞는것만 되도록 수정" ,
            description = "subjectid - subject 테이블에 과목 PK값" +
                    "<br>nm - 학생이름"+
                    "<br>categoryid - category 테이블에 카테고리pk값")
    List<SubjectDetailVo> subject(@RequestParam Long categoryid){
        return serivce.subject(categoryid);
    }

    @PostMapping
    @Operation(summary = "과목 등록",
    description = "입력값 : userid - 유저고유번호<br>" +
    "subjectid - 과목 번호<br>")
    int instcsbj(@RequestBody SubjectInsDto dto){
        return serivce.instcsbj(dto);
    }

    @GetMapping("/category/big")
    @Operation(summary = "등록후 과목계열 리스트"
    ,description = "입력값 : userid - 유저 pk값<br> "+
            "출력값 : subjectid - subject 테이블에 과목 PK값" +
            "<br>nm - 학생이름"+
            "<br>userid - 학생 pk값")
    List<SubjectDetailVo2> tcslist(@RequestParam Long userid)
    {

        SubjectDetailDto dto = new SubjectDetailDto();
        dto.setUserid(userid);
        return serivce.tcslist(dto);
    }
    @GetMapping("/category/small")
    @Operation(summary = "등록후 세부과목 리스트"
            ,description = "출력값 : userid - 유저 pk값<br> "+
            "categoryid - category 테이블에 세부과목 PK값" +
            "<br>nm - 학생이름"+
            "<br>입력값 : " +"<br>userid - 학생 pk값")
    List<SubjectVo2> smalllist(@AuthenticationPrincipal MyUserDetails user, @RequestParam Long userid){

        return serivce.smalllist(userid);
    }
    @GetMapping("/classnum")
    @Operation(summary = "반석차 반전체인원") //근데이거 calss id 19번인가 넘어가야 2번째학교 나옴 흠; 애매 차라리 컬럼을 늘리는게 이쁘긴할듯
    int classnum(@RequestParam Long classid){
        StudentClassDto dto = new StudentClassDto();
        dto.setClassid(classid);
        return serivce.classnum(dto);
    }

    @GetMapping("/schoolsnum")
    @Operation(summary = "학교 학년전체인원")
    int schoolnum(@AuthenticationPrincipal MyUserDetails user){

        return serivce.schoolnum(user);
    }
    @PostMapping("/acains")
    @Operation(summary = "학생별 내신성적등록")
    int acasubject(@RequestBody AcalistDto dto){
        return serivce.acasubject(dto);
    }
    //모의고사 시작
    @GetMapping("/mockbiglist")
    @Operation(summary = "모의고사 계열선택List")
    List<MockSubjectBigVo> mockbiglist(){
        return serivce.mockbiglist();
    }
    @GetMapping("/mocksmalllist")
    @Operation(summary = "모의고사 세부과목선택List 과목계열선택되면 그조건에 맞는것만 되도록 수정")
    List<MockSubjcetSmallVo> mocksmalllist(Long categoryid){
        return serivce.mocksmalllist(categoryid);
    }
    @PostMapping("/mockins")
    @Operation(summary = "모의고사 성적등록")
    int mockins(@RequestBody mockDto dto){
        return serivce.mockins(dto);
    }
}
