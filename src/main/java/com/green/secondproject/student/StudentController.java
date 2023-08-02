package com.green.secondproject.student;

import com.green.secondproject.student.model.StudentAcaResultVo;
import com.green.secondproject.student.model.StudentAcaResultsParam;
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
    @Operation(summary = "모의고사성적관리 테이블(메인하단)",  description = "요구값 : <br>" + "(1)userId - 학생고유코드<br>"+"(2)year - 조회기준연도(yyyy) <br>"+"(3)mon - 조회기준월(1~12)<br><br>"
            +"※※요구값 입력시 year, mon없이 학생번호로만 조회하려면 \"\" 형태로 입력해주셔야합니다※※"+
            "출력값 : <br>" + "(1)year - 연도<br>"+"(2)mon - 월<br>"+
            "(3)nm - 과목이름<br>"+"(4)cateNm - 과목계열이름 <br>"+"(5)standardScore - 표준점수<br><br> "+"(6)rating - 등급<br> (7)percent - 백분위")
    public List<StudentMockResultVo> selMockTestResultByDates(@RequestBody StudentMockResultsParam param){
        return service.selMockTestResultByDates(param);
    }


    @GetMapping("/acatable")
    @Operation(summary = "내신성적관리 테이블",  description = "요구값 : <br>" + "(1)userId - 학생고유코드<br>"+"(2)year - 조회기준연도(yyyy) <br>"+"(3)semester - 학기(1,2)<br>"+"(4)midFinal - (1:중간,2:기말)<br><br>"
            +"※※학생id외 요구값들은 선택사항입니다. 요구값없이 학생성적 조회를 원하시면 \"\" 형태로 요청해야합니다※※"+
            "출력값 : <br>" + "(1)year - 연도<br>"+"(2)semester - 학기(1,2)<br>"+
            "(3)midFinal - 1:중간2:기말<br>"+"(4)cateNm - 계열이름 <br>"+"(5)nm - 과목명 <br>"+"(6)score - 원점수<br><br> "+"(7)rating - 등급<br> (7)classRank - 반석차<br><br>(8)classRank - 전교석차<br><br>"
            +"※※1-중간고사 mon없이 학생번호로만 조회하려면 \"\" 형태로 입력해주셔야합니다※※")
    public List<StudentAcaResultVo> selAcaTestResultByDatesAndPeriod(@RequestBody StudentAcaResultsParam param){
        return service.selAcaTestResultByDatesAndPeriod(param);
    }
}
