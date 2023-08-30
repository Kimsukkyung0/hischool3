package com.green.secondproject.admin.schoolsubject;


import com.green.secondproject.admin.schoolsubject.model.ScSbjListVo;
import com.green.secondproject.admin.schoolsubject.model.ScSbjVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    ResponseEntity<List<ScSbjListVo>> regScSbj(@RequestParam List<Long> subjectIdList, @RequestParam int grade) {
        List<ScSbjListVo> scSbjVoList = service.saveAll(subjectIdList,grade);
        return ResponseEntity.ok(scSbjVoList);
    }


    @GetMapping("/category/big")
    @Operation(summary = "학년별 과목계열 리스트(수정중-중복값처리x)"
            , description = """
                    입력값 :<br> (1)grade : 조회대상 학년(1-3)<br><br>
                    출력값 : <br>(1)subjectid - subject 테이블에 과목 PK값
                    <br>(2)nm - 카테고이름
                    """)
    List<ScSbjVo> adminList(int grade) {
        return service.tcslist(grade);
    }

//    @GetMapping("/category/small")
//    @Operation(summary = "등록후 세부과목 리스트"
//            , description =
//            "categoryid - category 테이블에 세부과목 PK값" +
//                    "<br>nm - 학생이름")
//    List<ScSbjVo> smalllist(@RequestParam Long categoryId) {
//         return service.smalllist(categoryId);
//    }

    @DeleteMapping
    @Operation(summary = "과목 삭제")
    ResponseEntity<Integer> delScSbj(@RequestParam Long scSbjId)
    { return ResponseEntity.ok(service.delete(scSbjId)); }


}
