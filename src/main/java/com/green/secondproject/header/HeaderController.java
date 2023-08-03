package com.green.secondproject.header;

import com.green.secondproject.header.model.SelSchoolInfoVo;
import com.green.secondproject.header.model.SelSchoolLogoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/header")
@Tag(name = "헤더")
public class HeaderController {
    private final HeaderService service;


    @GetMapping("/school_info")
    @Operation(summary = "학생의 00고등학교 0학년 반 표기",
            description = "요구값 : <br> userId - 학생 PK값<br>")
    public List<SelSchoolInfoVo> SelectSchoolInfo(@RequestParam Long userId) {
        return service.selSchoolInfo(userId);
    }

    @GetMapping("/school_logo")
    @Operation(summary = "학생의 소속 학교 로고 표기",
            description = "요구값 : <br> userId - 학생 PK값<br>"+
                        "")
    public List<SelSchoolLogoVo> SelectSchoolLogo(@RequestParam Long userId) {
        return service.selSchoolLogo(userId);
    }
}
