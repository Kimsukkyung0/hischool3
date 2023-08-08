package com.green.secondproject.teacher.subject;

import com.green.secondproject.config.security.model.MyUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@ToString
@RequestMapping("/api/subject")
@Tag(name = "학급 및 학교 학생수")
public class SubjectController2 {
    private final SubjectSerivce serivce;


    @GetMapping("/class-num")
    @Operation(summary = "반석차 반전체인원")
    int classnum(@AuthenticationPrincipal MyUserDetails user){

        return serivce.classnum(user);
    }

    @GetMapping("/school-snum")
    @Operation(summary = "학교 학년전체인원")
    int schoolnum(@AuthenticationPrincipal MyUserDetails user){
        return serivce.schoolnum(user);
    }
}