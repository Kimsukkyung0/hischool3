package com.green.secondproject.student;

import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.student.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
@Tag(name = "학생")
public class StudentController {
    private final StudentService service;



    @DeleteMapping
    @Operation(summary = "학생 탈퇴 처리")
    public int deleteStudent(@RequestParam int userId) {
        return service.delStudent(userId);
    }

    @GetMapping("/mock-table")
    @Operation(summary = "모의고사성적관리 테이블",  description = "요구값 : <br>" + "(1)year - 조회기준연도(yyyy) <br>"+"(2)mon - 조회기준월(1~12)<br><br>"+
            "<br><br>출력값 : <br>" + "(1)year - 연도<br>"+"(2)mon - 월<br>"+
            "(3)nm - 과목이름<br>"+"(4)cateNm - 과목계열이름 <br>"+"(5)standardScore - 표준점수<br> "+"(6)rating - 등급<br> (7)percent - 백분위 ※ 연도 - 월 - 과목계열 내림차순 / 수정완료※<br>")
    public List<StudentMockSumResultVo> selMockTestResultByDates(@AuthenticationPrincipal MyUserDetails myuser, @RequestParam(required = false) String year, @RequestParam(required = false) String mon){
        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
        dto.setUserId(myuser.getUserId());
        dto.setYear(year);
        dto.setMon(mon);
        return service.selMockTestResultByDates(dto);
    }

    @GetMapping("/mock-highrating")
    @Operation(summary = "모의고사-학생별 가장 높은 등급",  description =
            "출력값 : <br>" + "(1)nm - 과목명<br>"+"(2)rating - 등급(1-9)<br>"+"※국어,수학,영어 한국사※")
    public List<StudentSummarySubjectVo> getHighestRatingsOfMockTest(@AuthenticationPrincipal MyUserDetails myuser){
        Long userId =  myuser.getUserId();
        return service.getHighestRatingsOfMockTest(userId);
    }

    @GetMapping("/mock-currentrating" )
    @Operation(summary = "모의고사- 최근응시 시험 등급",  description =
            "출력값 : <br>" + "(1)nm - 과목명<br>"+"(2)rating - 등급(1-9)<br>"
            +"※국어,수학,영어 한국사※")
    public List<StudentSummarySubjectVo> getLatestRatingsOfMockTest(@AuthenticationPrincipal MyUserDetails myuser){
        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
        dto.setUserId(myuser.getUserId());
        return service.getLatestRatingsOfMockTest(dto);
    }

    @GetMapping("/mock-graph")
    @Operation(summary = "모의고사그래프-올해 응시시험 성적",  description =
            "출력값 : <br>" + "(1)nm - 과목명<br>"+"(2)rating - 등급(1-9)<br>"
            +"※국수영한 순서/수정완료※")
    public List<StudentTestSumGraphVo> getMockTestSumGraph(@AuthenticationPrincipal MyUserDetails myuser){
        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
        dto.setUserId(myuser.getUserId());
        return service.getMockTestGraph(dto);
    }




    @GetMapping("/aca-table")
    @Operation(summary = "내신성적관리 테이블",  description = "요구값(선택) : <br>" + "(1)year - 조회기준연도(yyyy) <br>(2)semester - 학기(1,2)<br>(3)midFinal - (1:중간,2:기말)<br><br>"
            +"※※요구값들은 선택사항입니다. 요구값없이 학생성적 조회시 전체조회※※<br><br>"+
            "출력값 : <br>" + "(1)year - 연도<br>(2)semester - 학기(1,2)<br>"+
            "(3)midFinal - 1:중간2:기말<br>"+"(4)cateNm - 계열이름 <br>"+"(5)nm - 과목명 <br>"+"(6)score - 원점수<br>"+"(7)rating - 등급<br> (8)classRank - 반석차<br>(9)classRank - 전교석차<br><br>※ 연도-학기-시험구분 최신순으로 / 수정완료※<br>")
    public List<StudentAcaResultVo> selAcaTestResultByDatesAndPeriod(@AuthenticationPrincipal MyUserDetails myuser, @RequestParam(required = false) String year, @RequestParam(required = false) Integer semester, @RequestParam(required = false) Integer midFinal){
        StudentAcaResultsParam param = new StudentAcaResultsParam();
        param.setUserId(myuser.getUserId());
        param.setYear(year);
        param.setSemester(semester);
        param.setMidFinal(midFinal);
        return service.selAcaTestResultByDatesAndPeriod(param);
    }
    @GetMapping("/aca-highest")
    @Operation(summary = "내신:학생별 최고 등급",  description ="출력값 : <br>"+
            "(1)nm - 과목계열이름<br>(2)rating - 등급<br>")
    public List<StudentSummarySubjectVo> getHighestRatingsOfAcaTest(@AuthenticationPrincipal MyUserDetails myuser){
        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
        dto.setUserId(myuser.getUserId());
        return service.getHighestRatingsOfAcaTest(dto);
    }

    @GetMapping("/aca-latest")
    @Operation(summary = "내신:학생별 최신성적",  description ="출력값 : <br>"+ "date - (연도)-(학기) (중간/기말) "
            + "세부리스트 항목 : <br>(1)nm - 과목계열이름<br>(2)rating - 등급<br>※수정완료※<br>")
    public StudentSumContainerVo getLatestRatingsOfAcaTest(@AuthenticationPrincipal MyUserDetails myuser){
        Long userId =  myuser.getUserId();
        return service.getLatestRatingsOfAcaTest(userId);
    }

    @GetMapping("/aca-graph")
    List<StudentTestSumGraphVo> getAcaTestGraph(@AuthenticationPrincipal MyUserDetails myuser){
        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
        Long userId =  myuser.getUserId();
        dto.setUserId(userId);
        return service.getAcaTestGraph(dto);
    }
}
