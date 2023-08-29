package com.green.secondproject.admin.schoolsubject.model;


import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.teacher.subject.model.SubjectDetailVo2;
import com.green.secondproject.teacher.subject.model.SubjectDto;
import com.green.secondproject.teacher.subject.model.SubjectVo2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @Operation(summary = "등록후 과목계열 리스트"
            , description =
            "출력값 : subjectid - subject 테이블에 과목 PK값" +
                    "<br>nm - 세부과목이름")
    List<ScSbjVo> adminList() {
        return service.tcslist();
    }

//    @GetMapping("/category/small")
//    @Operation(summary = "등록후 세부과목 리스트"
//            , description =
//            "categoryid - category 테이블에 세부과목 PK값" +
//                    "<br>nm - 학생이름")
//    List<ScSbjVo> smalllist(@RequestParam Long categoryId) {
//         return service.smalllist(categoryId);
//    }

//    @DeleteMapping
//    @Operation(summary = "과목 삭제")
//    ResponseEntity<Integer> delScSbj(@RequestParam Long subjectId){
//        service.delete();
//        return ResponseEntity.ok(1);
//    }


}
