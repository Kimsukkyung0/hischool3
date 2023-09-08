package com.green.secondproject.student;

import com.green.secondproject.admin.model.NoticeTeacherListVo;
import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.student.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
@Tag(name = "학생")
public class StudentController {
    private final AuthenticationFacade facade;
    private final StudentService service;

    @GetMapping("/mock-table")
    @Operation(summary = "모의고사성적관리 테이블", description = "요구값 : <br>" + "(1)year - 조회기준연도(yyyy) <br>" + "(2)mon - 조회기준월(1~12)<br><br>" +
            "<br><br>출력값 : <br>" + "(1)year - 연도<br>" + "(2)mon - 월<br>" +
            "(3)nm - 과목이름<br>" + "(4)cateNm - 과목계열이름 <br>" + "(5)standardScore - 표준점수<br> " + "(6)rating - 등급<br> (7)percent - 백분위 ※ 연도 - 월 - 과목계열 내림차순 / 수정완료※<br>")
    public List<StudentMockSumResultWithIdVo> selMockTestResultByDates(@AuthenticationPrincipal MyUserDetails myuser, @RequestParam(required = false) String year, @RequestParam(required = false) String mon) {
        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
        dto.setUserId(myuser.getUserId());
        dto.setYear(year);
        dto.setMon(mon);
        return service.selMockTestResultByDates(dto);
    }

    @GetMapping("/aca-table")
    @Operation(summary = "내신성적관리 테이블", description = "요구값(선택) : <br>" + "(1)year - 조회기준연도(yyyy) <br>(2)semester - 학기(1,2)<br>(3)midFinal - (1:중간,2:기말)<br><br>"
            + "※※요구값들은 선택사항입니다. 요구값없이 학생성적 조회시 전체조회※※<br><br>" +
            "출력값 : <br>" + "(1)year - 연도<br>(2)semester - 학기(1,2)<br>" +
            "(3)midFinal - 1:중간2:기말<br>" + "(4)cateNm - 계열이름 <br>" + "(5)nm - 과목명 <br>" + "(6)score - 원점수<br>" + "(7)rating - 등급<br> (8)classRank - 반석차<br>(9)classRank - 전교석차<br><br>※ 연도-학기-시험구분 최신순으로 / 수정완료※<br>")
    public List<StudentAcaResultWithIdVo> selAcaTestResultByDatesAndPeriod(@AuthenticationPrincipal MyUserDetails myuser, @RequestParam(required = false) String year, @RequestParam(required = false) Integer semester, @RequestParam(required = false) Integer midFinal) {
        StudentAcaResultsParam param = new StudentAcaResultsParam();
        param.setUserId(myuser.getUserId());
        param.setYear(year);
        param.setSemester(semester);
        param.setMidFinal(midFinal);
        param.setVanId(myuser.getVanId());
        param.setGrade(myuser.getGrade());
        param.setSchoolId(myuser.getSchoolId());
        return service.selAcaTestResultByDatesAndPeriod(param);
    }





    @GetMapping("/notice")
    @Operation(summary = "학생 페이지 공지사항", description = """
            "imptList": 중요공지 리스트<br>
            "normalList": 일반공지 리스트<br>
            "noticeId": 공지사항 PK,<br>
            "imptYn": 중요(1) 일반(0),<br>
            "title": 제목,<br>
            "createdAt": 작성일,<br>
            "hits": 조회수 """)
    public NoticeTeacherListVo NoticeTeacher() {
        return service.NoticeTeacher();
    }


    //3차 JPA 적용부분////////////////////3차 JPA 적용부분//////////////////

    @GetMapping("/aca-latest")
    @Operation(summary = "내신 : 학생별 최신성적", description = "출력값 : <br>" + "date - (연도)-(학기) (중간/기말) "
            + "세부리스트 항목 : <br>(1)nm - 과목계열이름<br>(2)rating - 등급<br>※수정완료※<br>")
    public StudentSumContainerVo getLatestRatingsOfAcaTest() {
        return service.getLatestRatingsOfAcaTest();
    }


    @GetMapping("/aca-highest")
    @Operation(summary = "내신:학생별 최고 등급", description = "출력값 : <br>" +
            "(1)nm - 과목계열이름<br>(2)rating - 등급<br>")
    public List<StudentSummarySubjectVo> getHighestRatingsOfAcaTest() {
        return service.getHighestRatingsOfAcaTest();
    }

    @GetMapping("/mock-latest")
    @Operation(summary = "모의고사- 최근응시 시험 등급", description =
            "출력값 : <br>" + "(1)nm - 과목명<br>" + "(2)rating - 등급(1-9)<br>"
                    + "※국어,수학,영어 한국사※")
    public StudentSumContainerVo getLatestRatingsOfMockTest() {
        return service.getLatestRatingsOfMockTest();
    }

    @GetMapping("/mock-highest")
    @Operation(summary = "모의고사-학생별 가장 높은 등급", description =
            "출력값 : <br>" + "(1)nm - 과목명<br>" + "(2)rating - 등급(1-9)<br>" + "※국어,수학,영어 한국사※")
    public List<StudentSummarySubjectVo> getHighestRatingsOfMockTest(@AuthenticationPrincipal MyUserDetails myuser) {
        Long userId = myuser.getUserId();
        return service.getHighestRatingsOfMockTest(userId);
    }

    @GetMapping("/mock-download")
    @Operation(summary = "모의고사 성적 다운로드")
    public void downloadMock(HttpServletResponse res, @ParameterObject StudentSummarySubjectDto dto) throws IOException {
        service.downloadMock(res, dto);
    }

    @GetMapping("/aca-download")
    @Operation(summary = "내신 성적 다운로드")
    public void downloadAca(HttpServletResponse res, @ParameterObject StudentAcaResultsParam p) throws IOException {
        service.downloadAca(res, p);
    }

    @GetMapping("/aca-graph")
    @Operation(summary = "내신그래프:올해 응시시험 성적", description = """
            출력값 : <br>" + "(1)date - (연도)-(학기) (중간/기말)<br> 
            (2)nm - 과목계열이름<br>
            (3)rating - 등급<br>※수정완료※<br>""")
    List<StudentTestSumGraphVo> getAcaTestGraph() {
        return service.getAcaTestGraph(facade.getLoginUserPk());
    }

    @GetMapping("/mock-graph")
    @Operation(summary = "모의고사그래프-올해 응시시험 성적", description =
            "출력값 : <br>" + "(1)nm - 과목명<br>" + "(2)rating - 등급(1-9)<br>"
                    + "※국수영한 순서/수정완료※")
    public List<StudentTestSumGraphVo> getMockTestSumGraph() {
        return service.getMockTestGraph(facade.getLoginUserPk());
    }
}
