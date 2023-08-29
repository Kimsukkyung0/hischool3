package com.green.secondproject.admin.schoolsubject.model;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
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
//
//    @PostMapping
//    @Operation(summary = "과목 등록",
//            description = "subjectid - 과목 번호<br>")
//    String regScSbj(@RequestParam List<Long> subjectIdList, @RequestParam Grade grade) {
//        return service.regScSbj(subjectIdList,grade);
//    }


}
