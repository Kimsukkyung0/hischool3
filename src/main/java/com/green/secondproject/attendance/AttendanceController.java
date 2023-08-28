package com.green.secondproject.attendance;

import com.green.secondproject.attendance.model.AttendanceIns;
import com.green.secondproject.attendance.model.AttendanceUpd;
import com.green.secondproject.common.entity.AttendanceEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
@Tag(name = "출결 현황")
public class AttendanceController {
    private final AttendanceService service;

    @PostMapping
    @Operation(summary = "출결 현황 입력", description = """
          "userId": 선택한 학생 PK<br>
          "grade": ex) "1"<br>
          "lessonNum": 수업 일수<br>
          "diseaseAbsence": 질병 결석<br>
          "unauthAbsence": 무단 결석<br>
          "etcAbsence": 기타 결석<br>
          "diseaseLate": 질병 지각<br>
          "unauthLate": 무단 지각<br>
          "etcLate": 기타 지각<br>
          "diseaseEarly": 질병 조퇴<br>
          "unauthEarly": 무단 조퇴<br>
          "etcEarly": 기타 조퇴<br>
          "diseaseOut": 질병 결과<br>
          "unauthOut": 무단 결과<br>
          "etcOut": 기타 결과<br>
          "specialNote": 특이 사항
          """)
    public AttendanceEntity postAttendance(@RequestBody AttendanceIns p) {
        return service.insAttendance(p);
    }

    @GetMapping
    @Operation(summary = "출결 현황 조회", description = """
          입력값 -<br>
          "userId": 선택한 학생 PK(학생이 조회할 경우 userId 미입력)<br><br>
          출력값 -<br>
          "attendId": 출결 현황 PK<br>
          "grade": 학년<br>
          "lessonNum": 수업 일수<br>
          "diseaseAbsence": 질병 결석<br>
          "unauthAbsence": 무단 결석<br>
          "etcAbsence": 기타 결석<br>
          "diseaseLate": 질병 지각<br>
          "unauthLate": 무단 지각<br>
          "etcLate": 기타 지각<br>
          "diseaseEarly": 질병 조퇴<br>
          "unauthEarly": 무단 조퇴<br>
          "etcEarly": 기타 조퇴<br>
          "diseaseOut": 질병 결과<br>
          "unauthOut": 무단 결과<br>
          "etcOut": 기타 결과<br>
          "specialNote": 특이 사항
          """)
    public List<AttendanceEntity> getAttendance(@RequestParam(required = false) Long userId) {
        return service.getAttendance(userId);
    }

    @PutMapping
    @Operation(summary = "출결 현황 수정", description = """
          "attendId": 수정할 출결 현황 PK<br>
          "lessonNum": 수업 일수<br>
          "diseaseAbsence": 질병 결석<br>
          "unauthAbsence": 무단 결석<br>
          "etcAbsence": 기타 결석<br>
          "diseaseLate": 질병 지각<br>
          "unauthLate": 무단 지각<br>
          "etcLate": 기타 지각<br>
          "diseaseEarly": 질병 조퇴<br>
          "unauthEarly": 무단 조퇴<br>
          "etcEarly": 기타 조퇴<br>
          "diseaseOut": 질병 결과<br>
          "unauthOut": 무단 결과<br>
          "etcOut": 기타 결과<br>
          "specialNote": 특이 사항
          """)
    public AttendanceEntity putAttendance(@RequestBody AttendanceUpd p) {
        return service.updAttendance(p);
    }
}
