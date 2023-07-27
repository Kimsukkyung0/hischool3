package com.green.secondproject.teacher.subject;

import com.green.secondproject.teacher.subject.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ToString
@RequestMapping("/api/teacher/subject")
@Tag(name = "과목")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectSerivce serivce;


    @GetMapping("/category")
    @Operation(summary = "선생님 과목계열 리스트")
    List<SubjectVo> subcate(){
        return serivce.subcate();
    }

    @GetMapping()
    @Operation(summary = "선생님 세부과목 리스트")
    List<SubjectDetailVo> subject(){
        return serivce.subject();
    }

    @PostMapping
    @Operation(summary = "과목 등록")
    int instcsbj(@RequestBody SubjectInsDto dto){
        return serivce.instcsbj(dto);
    }

    @GetMapping("/category/big")
    @Operation(summary = "등록후 과목계열 리스트")
    List<SubjectDetailVo2> tcslist(@RequestParam long user_id)
    {
        SubjectDetailDto dto = new SubjectDetailDto();
        dto.setUser_id(user_id);
        return serivce.tcslist(dto);
    }
    @GetMapping("/category/small")
    @Operation(summary = "등록후 세부과목 리스트")
    List<SubjectVo2> smalllist(@RequestParam long user_id){
        SubjectDto dto = new SubjectDto();
        dto.setUser_id(user_id);
        return serivce.smalllist(dto);
    }
}
