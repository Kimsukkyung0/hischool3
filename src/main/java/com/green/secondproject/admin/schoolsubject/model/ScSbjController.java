package com.green.secondproject.admin.schoolsubject.model;


import com.green.secondproject.common.config.etc.Grade;
import com.green.secondproject.common.entity.ScSbjEntity;
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
            description = "subjectid - 과목 번호<br>")
    ResponseEntity<List<ScSbjVo>> regScSbj(@RequestParam List<Long> subjectIdList, @RequestParam Grade grade) {
        List<ScSbjVo> scSbjVoList = service.saveAll(subjectIdList,grade);
        return ResponseEntity.ok(scSbjVoList);
    }

}
