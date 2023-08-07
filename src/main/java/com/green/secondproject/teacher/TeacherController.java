package com.green.secondproject.teacher;

import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.student.StudentService;
import com.green.secondproject.student.model.StudentAcaResultVo;
import com.green.secondproject.student.model.StudentAcaResultsParam;
import com.green.secondproject.student.model.StudentMockSumResultVo;
import com.green.secondproject.student.model.StudentSummarySubjectDto;
import com.green.secondproject.teacher.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@Tag(name = "선생님")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherSerivce service;
    private final StudentService studentService;

    @GetMapping("/signed")
    @Operation(summary = "승인된 학생 리스트",
            description = "요구값 : <br>(1)classId - 학급 PK값<br><br>"+
                    "출력값 : <br>(1)userId - 학생 PK값<br>(2)classId - 학급 PK값<br>" +
                    "(3)aprYn - 승인여부 (0 = 승인대기, 1 = 승인)<br>(4)snm - 학생 이름<br>" +
                    "(5)birth - 생일<br>(6)phone - 연락처<br>(7)email - 이메일")
    public List<SelSignedStudentVo> selectSignedStudent(@AuthenticationPrincipal MyUserDetails myuser) {
        return service.selSignedStudent(myuser);
    }


    @GetMapping("/unsigned")
    @Operation(summary = "승인된 대기 명단",
            description = "요구값 : <br>(1)classId - 학급 PK값<br><br>"+
                    "(3)aprYn - 승인여부 (0 = 승인대기, 1 = 승인)"+
                    "<br>(4)snm - 학생 이름<br>(5)birth - 생일<br>(6)phone - 연락처<br>(7)email - 이메일")
    public List<SelUnsignedStudentVo> selectUnsignedStudent(@AuthenticationPrincipal MyUserDetails myuser) {
        return service.selUnsignedStudent(myuser);
    }


    @GetMapping("/acaresult")
    @Operation(summary = "학생 내신 성적 조회",
            description = "요구값 : <br>(1)userId - 유저(학생) PK값<br><br>"+
                    "출력값 : <br>(1)resultId - 성적 PK값<br>(2)year - 년도<br>(3)semester - 학기<br>" +
                    "(4)categoryId - 과목 계열<br>(5)nm - 세부 과목(6)mf - 시험구분[1-중간/2-기말]<br>" +
                    "(7)score - 점수<br>(8)rating - 등급<br>(9)cr - 반 석차<br>(10)wr - 전교석차")
    public List<SelAcaResultVo> selectAcaResult(@RequestParam Long userId) {
        return service.selAcaResult(userId);
    }


    @GetMapping("/mockresult")
    @Operation(summary = "학생 모의고사 성적 조회",
            description = "요구값 : <br>(1)userId - 유저(학생) PK값<br><br>"+
                    "출력값 : <br>(1)resultId - 성적 PK값<br>(2)year - 년도<br>(3)mon - 월<br>" +
                    "(4)categoryId - 과목 계열<br>(5)nm - 세부 과목<br>(6)sc - 표준 점수<br>" +
                    "(7)rating - 등급<br>(8)percent - 백분위")

    public List<SelMockResultVo> selectMockResult(@RequestParam Long userId) {
        return service.selMockResult(userId);
    }

    @PatchMapping("/accept-student")
    public int acceptStudent(@RequestParam Long userId) {
        return service.acceptStudent(userId);
    }

    @PatchMapping("/reject-student")
    public int rejectStudent(@RequestParam Long userId) {
        return service.acceptStudent(userId);
    }


    @DeleteMapping
    @Operation(summary = "선생님 탈퇴 처리",
            description = "요구값 : <br>(1)userId - 유저(선생님) PK값<br><br>"+
                            "출력값 : <br>(1)del_yn = 1 -> 삭제 처리 완료")
    public int deleteTeacher(@RequestParam Long userId) {
        return service.delTeacher(userId);
    }

    @GetMapping("/class-student")
    @Operation(summary = "반 학생총원")
    public int classStudent(@RequestParam Long classid){
        ClassStudentDto dto = new ClassStudentDto();
        dto.setClassid(classid);

        return service.classStudent(dto);
    }

    @GetMapping("/apr-student")
    @Operation(summary = "승인 대기 인원(n명)")
    public int aprStudent(@RequestParam Long classid){
        return service.aprStudent(classid);
    }

    @GetMapping("/aca-grape")
    @Operation(summary = "등급 출력")
    public List<TeacherGrapeVo> teacherGrap(@RequestParam int count,@RequestParam Long classid, @RequestParam Long categoryid ){
        TeacherVanGrapeDto dto = new TeacherVanGrapeDto();
        dto.setCategoryid(categoryid);
        dto.setCount(count);
        dto.setClassid(classid);
        return service.teacherGrap(dto);
    }

    @GetMapping("/aca/{id}")
    @Operation(summary = "선생님-학생별 내신성적관리 테이블(하단)",  description = "요구값(필수) : <br>\" + \"(1)userId - 학생pk <br>요구값(선택) : <br>" + "(1)year - 조회기준연도(yyyy) <br>(2)semester - 학기(1,2)<br>(3)midFinal - (1:중간,2:기말)<br><br>"
            +"※※학생 아이디 외 요구값들은 선택사항입니다. 요구값없이 조회시 전체조회※※<br><br>"+
            "출력값 : <br>" + "(1)year - 연도<br>(2)semester - 학기(1,2)<br>"+
            "(3)midFinal - 1:중간2:기말<br>"+"(4)cateNm - 계열이름 <br>"+"(5)nm - 과목명 <br>"+"(6)score - 원점수<br>"+"(7)rating - 등급<br> (8)classRank - 반석차<br>(9)classRank - 전교석차<br><br>※ 연도-학기-시험구분 최신순으로 / 수정완료※<br>")
    public List<StudentAcaResultVo> selAcaTestResultByDatesAndPeriod(@PathVariable(value = "id") Long userId, @RequestParam(required = false) String year, @RequestParam(required = false) Integer semester, @RequestParam(required = false) Integer midFinal){
        StudentAcaResultsParam param = new StudentAcaResultsParam();
        param.setUserId(userId);
        param.setYear(year);
        param.setSemester(semester);
        param.setMidFinal(midFinal);
        return studentService.selAcaTestResultByDatesAndPeriod(param);
    }

    @GetMapping("/mock/{id}")
    @Operation(summary = "선생님-학생별 모의고사성적관리 테이블(하단)",  description = "요구값(필수) : <br>\" + \"(1)userId - 학생pk <br>요구값 : <br>" + "(1)year - 조회기준연도(yyyy) <br>"+"(2)mon - 조회기준월(1~12)<br><br>"+
            "<br><br>출력값 : <br>" + "(1)year - 연도<br>"+"(2)mon - 월<br>"+
            "(3)nm - 과목이름<br>"+"(4)cateNm - 과목계열이름 <br>"+"(5)standardScore - 표준점수<br> "+"(6)rating - 등급<br> (7)percent - 백분위 ※ 연도 - 월 - 과목계열 내림차순 / 수정완료※<br>")
    public List<StudentMockSumResultVo> selMockTestResultByDates(@PathVariable(value = "id") Long userId,@RequestParam(required = false) String year, @RequestParam(required = false) String mon){
        StudentSummarySubjectDto dto = new StudentSummarySubjectDto();
        dto.setUserId(userId);
        dto.setYear(year);
        dto.setMon(mon);
        return studentService.selMockTestResultByDates(dto);
    }

}
