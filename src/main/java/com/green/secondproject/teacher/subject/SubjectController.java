package com.green.secondproject.teacher.subject;

import com.green.secondproject.teacher.subject.model.SubjectDetailVo;
import com.green.secondproject.teacher.subject.model.SubjectVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/subject")
@Tag(name = "과목")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectSerivce serivce;





    @GetMapping("/subCate")
    @Operation(summary = "과목계열")
    public List<SubjectVo> sbjCate(){
        return serivce.sbjCate();
    }

    @GetMapping
    @Operation(summary = "세부과목")
    public List<SubjectDetailVo> subDetail() {
        return serivce.subDetail();
    }
}
