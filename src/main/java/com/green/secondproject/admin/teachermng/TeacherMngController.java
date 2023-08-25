package com.green.secondproject.admin.teachermng;


import com.green.secondproject.admin.teachermng.model.TeacherMngVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin/tc")
@RequiredArgsConstructor
@Tag(name = "관리자-교원관리")
public class TeacherMngController {
        private final TeacherMngService teacherMngService;

    @GetMapping
    List<TeacherMngVo> teacherNotapprovedList(Long schoolId){
        return teacherMngService.teacherNotapprovedList(schoolId);
    }

}
