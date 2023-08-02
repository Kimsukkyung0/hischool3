package com.green.secondproject.student;

import com.green.secondproject.student.model.StudentMockResultVo;
import com.green.secondproject.student.model.StudentMockResultsParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
@Tag(name = "학생")
public class StudentController {
    private final StudentService service;



    @DeleteMapping("/delete")
    @Operation(summary = "학생 탈퇴 처리")
    public int deleteStudent(@RequestParam int userId) {
        return service.delStudent(userId);
    }

    @GetMapping("/mocktable")
    @Operation(summary = "모의고사성적관리 테이블",  description = "요구값 : <br>" + "(1)userId - 학생고유코드<br>"+"(2)year - 조회기준연도(yyyy) <br>"+"(3)mon - 조회기준월(1~12)<br><br>"
            +"※※요구값 입력시 year, mon없이 학생번호로만 조회하려면 \"\" 형태로 입력해주셔야합니다※※"+
            "출력값 : <br>" + "(1)year - 연도<br>"+"(2)mon - 월<br>"+
            "(3)nm - 과목이름<br>"+"(4)cateNm - 과목계열이름 <br>"+"(5)standardScore - 표준점수<br><br> "+"(6)rating - 등급<br> (7)percent - 백분위"
    )
    public List<StudentMockResultVo> selMockTestResultByDates(@RequestBody StudentMockResultsParam param){
        return service.selMockTestResultByDates(param);
    }
}
