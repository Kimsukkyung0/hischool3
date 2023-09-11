package com.green.secondproject.admin.schoolsubject;


import com.green.secondproject.admin.schoolsubject.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@ToString
@RequestMapping("/api/admin/sbj")
@Tag(name = "학교별 과목관리")
@RequiredArgsConstructor
public class ScSbjController {
    private final ScSbjService service;

    @PostMapping
    @Operation(summary = "과목 등록",
            description =  """
            요구값 : <br>(1)subjectIdList : 1개 이상의 과목번호 입력<br>(2)grade : 학년값(1-3학년)<br><br>
            출력값 : <br>(1)scSbjId : 등록번호 <br>
    (2)grade : 학년<br>
    (3)subjectId : 등록된 과목의 pk""")
    ResponseEntity<List<ScSbjListVo>> regScSbj(@RequestBody ScSbjListDto list, @RequestParam int grade) {
        List<ScSbjListVo> scSbjVoList = service.saveAll(list,grade);
        return ResponseEntity.ok(scSbjVoList);
    }
    @GetMapping("/cate")
    @Operation(summary = "관리자용 과목계열 전체 리스트 조회(내신)",
            description = """
    (1)categoryNm - 과목이름<br>" + "(2)categoryId - 과목 계열pk값
    """)
    List<ScCateVo> cateList() {
        return service.getCateList();
    }

    @GetMapping("/cate/{categoryId}")
    @Operation(summary = "관리자용 과목계열별 세부과목 리스트 조회(내신)",
            description = """  
            요구값 : <br>(1)categoryId : 과목계열 pk값 <br><br>
            출력값 : <br>(1)subjectId - 세부과목 pk값<br>(2)subjectNm - 과목이름<br> 
            *** 등록된 과목은 학교에 따라 과목 리스트에서 제외***<br>
                    """)
    List<ScSbjVo> getSubjectListByCate(@PathVariable Long categoryId) {
        return service.getSubjectListByCate(categoryId);
    }

    @GetMapping("/category/{grade}")
    @Operation(summary = "학년별 등록과목 리스트"
            , description = """
                    입력값 :<br> (1)grade : 조회대상 학년(1-3)<br><br>
                    출력값 : <br> (1)categoryId : 카테고리pk <br>(2)categoryNm - 카테고리이름<br>(3)subjectid -  과목 PK값<br> (4)subjectNm : 세부과목<br> 
                    (5)scSbjId : 학교에 등록된 과목리스트 pk<br> 
                    """)
    List<ScSbjGradeVo> adminList(@PathVariable int grade) {
        return service.adminSbjList(grade);
    }

    @DeleteMapping
    @Operation(summary = "학교별 과목 삭제")
    ResponseEntity<Integer> delScSbj(@RequestParam Long scSbjId)
    { return ResponseEntity.ok(service.delete(scSbjId)); }

    @PutMapping
    @Operation(summary = "학교별 과목수정"
            , description = """
                    입력값 :<br> (1)subjectId(List) : 수정대상 과목 id <br><br>
                    출력값 : <br> (1)categoryId : 카테고리pk <br>(2)categoryNm - 카테고리이름<br>(3)subjectid -  과목 PK값<br> (4)subjectNm : 세부과목<br> 
                    (5)scSbjId : 학교에 등록된 과목리스트 pk<br><br> 
                    **** 기존 목록을 자동으로 비교하여 사라진과목은 자동삭제/ 추가된 과목은 자동추가 됩니다 ****<br> 
                    """)
    ResponseEntity<List<ScSbjListVo2>> updSubjectsBySchoolAndGrade(@RequestBody List<SbjDto> list, @RequestParam int grade){
        List<ScSbjListVo2> vo = service.updSubjectsBySchoolAndGrade(list, grade);
        return ResponseEntity.ok(vo);
    }

}
