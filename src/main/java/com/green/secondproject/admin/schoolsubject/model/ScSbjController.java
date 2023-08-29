package com.green.secondproject.admin.schoolsubject.model;


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

//    @PostMapping
//    @Operation(summary = "과목 등록",
//            description = "subjectid - 과목 번호<br>")
//    ResponseEntity<List<ScSbjInsDto2>> regScSbj(@RequestParam List<Long> subjectIdList, @RequestParam Grade grade) {
//        List<ScSbjInsDto2> scSbjEntityList = service.saveAll(subjectIdList,grade);
//        return ResponseEntity.ok(scSbjEntityList);
//    }
    //responseok surround
    @PostMapping
    @Operation(summary = "과목 등록",
            description =  """
            요구값 : <br>(1)subjectIdList : 1개 이상의 과목번호 입력<br>(2)grade : 학년값(1-3학년)<br><br>
            출력값 : <br>(1)scSbjId : 등록번호 <br>
    (2)grade : 학년<br>
    (3)subjectId : 등록된 과목의 pk """)
    ResponseEntity<List<ScSbjVo>> regScSbj(@RequestParam List<Long> subjectIdList, @RequestParam int grade) {
        List<ScSbjVo> scSbjVoList = service.saveAll(subjectIdList,grade);
        return ResponseEntity.ok(scSbjVoList);
    }

}
