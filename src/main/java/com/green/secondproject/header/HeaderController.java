package com.green.secondproject.header;

import com.green.secondproject.config.security.model.MyUserDetails;
import com.green.secondproject.header.model.SelSchoolInfoVo;
import com.green.secondproject.header.model.SelSchoolLogoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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


    @GetMapping("/school-info")
    @Operation(summary = "학생의 00고등학교 0학년 반 표기",
            description = "요구값 : <br> userId - 학생 PK값<br>" +
                        "출력값 : <br>(1)userId - 학생 PK값<br>(2)schoolId- 학교 PK값<br>" +
                        "(3)logo - 학교 로고<br>(4)nm - 학교명<br>(5)grade - 학년<br>(6)van - 반")
    public List<SelSchoolInfoVo> SelectSchoolInfo(@AuthenticationPrincipal MyUserDetails myuser) {
        return service.selSchoolInfo(myuser.getUserId());
    }

    @GetMapping("/school-logo")
    @Operation(summary = "학생의 소속 학교 로고 표기",
            description = "요구값 : <br> userId - 학생 PK값<br>"+
                        "출력값 : <br>(1)userId - 학생 PK값<br>(2)logo - 학교 로고")
    public List<SelSchoolLogoVo> SelectSchoolLogo(@AuthenticationPrincipal MyUserDetails myuser) {
        return service.selSchoolLogo(myuser);
    }
}
