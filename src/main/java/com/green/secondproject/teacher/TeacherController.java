package com.green.secondproject.teacher;

import com.green.secondproject.teacher.model.SelAcaResultVo;
import com.green.secondproject.teacher.model.SelMockResultVo;
import com.green.secondproject.teacher.model.SelSignedStudentVo;
import com.green.secondproject.teacher.model.SelUnsignedStudentVo;
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
                            "출력값 : <br>(1)classId - 학급 PK값<br>(2)aprYn - 승인여부 (0 = 승인대기, 1 = 승인)"+
                                    "<br>(3)unm - 학생 이름<br>(4)birth - 생일<br>(5)phone - 연락처<br>(6)email - 이메일")
    public List<SelSignedStudentVo> SelectSignedStudent(@RequestParam Long classId) {
        return service.selSignedStudent(classId);
    }


    @GetMapping("/unsigned")
    @Operation(summary = "승인된 대기 명단",
            description = "요구값 : <br>(1)classId - 학급 PK값<br><br>"+
                    "출력값 : <br>(1)classId - 학급 PK값<br>(2)aprYn - 승인여부 (0 = 승인대기, 1 = 승인)"+
                    "<br>(3)unm - 학생 이름<br>(4)birth - 생일<br>(5)phone - 연락처<br>(6)email - 이메일")
    public List<SelUnsignedStudentVo> SelectUnsignedStudent(@RequestParam Long classId) {
        return service.selUnsignedStudent(classId);
    }


    @GetMapping("/acaresult")
    @Operation(summary = "학생 내신 성적 조회(미완성입니다@@변경 예정)",
            description = "요구값 : <br>(1)userId - 유저(학생) PK값<br><br>"+
                    "출력값 : <br>(1)year - 년도<br>(2)mf - 시험구분(1-중간/2-기말)<br>" +
                    "(3)score - 점수<br>(4)rating - 등급<br>(5)cr - 반 석차<br>(6)wr - 전교석차")
    public List<SelAcaResultVo> SelectAcaResult(@RequestParam Long userId) {
        return service.selAcaResult(userId);
    }


    @GetMapping("/mockresult")
    @Operation(summary = "학생 모의고사 성적 조회(이것도 미완성입니다@@변경 예정)",
            description = "요구값 : <br>(1)userId - 유저(학생) PK값<br><br>"+
                    "출력값 : <br>(1)year - 년도<br>(2)mon - 월<br>" +
                    "(3)sc - 표준 점수<br>(4)rating - 등급<br>(5)percent - 백분위<br>(6)wr - 전교석차")
    public List<SelMockResultVo> SelectMockResult(@RequestParam Long userId) {
        return service.selMockResult(userId);
    }


    @DeleteMapping("/delete")
    @Operation(summary = "선생님 탈퇴 처리",
            description = "요구값 : <br>(1)userId - 유저(선생님) PK값<br><br>"+
                            "출력값 : <br>(1)del_yn = 1 -> 삭제 처리 완료")
    public int deleteTeacher(@RequestParam Long userId) {
        return service.delTeacher(userId);
    }
}
