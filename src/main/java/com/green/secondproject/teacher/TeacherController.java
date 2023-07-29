package com.green.secondproject.teacher;

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
    public List<SelSignedStudentVo> SelectSignedStudent(@RequestParam int classId) {
        return service.selSignedStudent(classId);
    }


    @GetMapping("/unsigned")
    @Operation(summary = "승인된 대기 명단",
            description = "요구값 : <br>(1)classId - 학급 PK값<br><br>"+
                    "출력값 : <br>(1)classId - 학급 PK값<br>(2)aprYn - 승인여부 (0 = 승인대기, 1 = 승인)"+
                    "<br>(3)unm - 학생 이름<br>(4)birth - 생일<br>(5)phone - 연락처<br>(6)email - 이메일")
    public List<SelUnsignedStudentVo> SelectUnsignedStudent(@RequestParam int classId) {
        return service.selUnsignedStudent(classId);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "선생님 탈퇴 처리",
            description = "요구값 : <br>(1)userId - 유저(선생님) PK값<br><br>"+
                            "출력값 : <br>(1)del_yn = (1 = 삭제 처리 완료)")
    public int deleteTeacher(@RequestParam int userId) {
        return service.delTeacher(userId);
    }
}
