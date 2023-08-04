package com.green.secondproject.teacher;

import com.green.secondproject.teacher.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@Tag(name = "선생님")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherSerivce service;


    @GetMapping("/signed")
    @Operation(summary = "승인된 학생 리스트",
            description = "요구값 : <br>(1)classId - 학급 PK값<br><br>"+
                    "출력값 : <br>(1)userId - 학생 PK값<br>(2)classId - 학급 PK값<br>" +
                    "(3)aprYn - 승인여부 (0 = 승인대기, 1 = 승인)<br>(4)snm - 학생 이름<br>" +
                    "(5)birth - 생일<br>(6)phone - 연락처<br>(7)email - 이메일")
    public List<SelSignedStudentVo> SelectSignedStudent(@RequestParam Long classId) {
        return service.selSignedStudent(classId);
    }


    @GetMapping("/unsigned")
    @Operation(summary = "승인된 대기 명단",
            description = "요구값 : <br>(1)classId - 학급 PK값<br><br>"+
                    "출력값 : <br>(1)userId - 학생 PK값<br>(2)classId - 학급 PK값<br>" +
                    "(3)aprYn - 승인여부 (0 = 승인대기, 1 = 승인)"+
                    "<br>(4)snm - 학생 이름<br>(5)birth - 생일<br>(6)phone - 연락처<br>(7)email - 이메일")
    public List<SelUnsignedStudentVo> SelectUnsignedStudent(@RequestParam Long classId) {
        return service.selUnsignedStudent(classId);
    }


    @GetMapping("/acaresult")
    @Operation(summary = "학생 내신 성적 조회",
            description = "요구값 : <br>(1)userId - 유저(학생) PK값<br><br>"+
                    "출력값 : <br>(1)resultId - 성적 PK값<br>(2)year - 년도<br>(3)semester - 학기<br>" +
                    "(4)categoryId - 과목 계열<br>(5)nm - 세부 과목(6)mf - 시험구분[1-중간/2-기말]<br>" +
                    "(7)score - 점수<br>(8)rating - 등급<br>(9)cr - 반 석차<br>(10)wr - 전교석차")
    public List<SelAcaResultVo> SelectAcaResult(@RequestParam Long userId) {
        return service.selAcaResult(userId);
    }


    @GetMapping("/mockresult")
    @Operation(summary = "학생 모의고사 성적 조회",
            description = "요구값 : <br>(1)userId - 유저(학생) PK값<br><br>"+
                    "출력값 : <br>(1)resultId - 성적 PK값<br>(2)year - 년도<br>(3)mon - 월<br>" +
                    "(4)categoryId - 과목 계열<br>(5)nm - 세부 과목<br>(6)sc - 표준 점수<br>" +
                    "(7)rating - 등급<br>(8)percent - 백분위")
    public List<SelMockResultVo> SelectMockResult(@RequestParam Long userId) {
        return service.selMockResult(userId);
    }


    @DeleteMapping("/delete-teacher")
    @Operation(summary = "선생님 탈퇴 처리",
            description = "요구값 : <br>(1)userId - 유저(선생님) PK값<br><br>"+
                            "출력값 : <br>(1)del_yn = 1 -> 삭제 처리 완료")
    public int deleteTeacher(@RequestParam Long userId) {
        return service.delTeacher(userId);
    }

    @GetMapping("/classStudent")
    @Operation(summary = "반 학생총원")
    public int classStudent(@RequestParam Long classid){
        ClassStudentDto dto = new ClassStudentDto();
        dto.setClassid(classid);

        return service.classStudent(dto);
    }

    @GetMapping("/aprStudent")
    @Operation(summary = "승인 대기 인원(n명)")
    public int aprStudent(@RequestParam Long classid){
        return service.aprStudent(classid);
    }
}
