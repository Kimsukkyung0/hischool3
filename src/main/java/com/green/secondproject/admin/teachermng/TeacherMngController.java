package com.green.secondproject.admin.teachermng;


import com.green.secondproject.admin.teachermng.model.TeacherMngVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.jfr.Description;
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
    @Operation(summary = "각 학교의 승인대기중 교원목록", description = "요구값 : schoolId : 학교고유코드<br>※※추후로그인기능으로수정예정※※<br><br>" +
            "출력값 : <br><br> (1)userId : 유저pk<br>(2)classId : 반 코드 <br> (3)email : 교원email <br> (4)nm : 교원이름<br> (5)birth : 생년월일<br>(6)phone :교원연락처<br>"+
            "(7)address : 상위주소<br> (8)detailAddr : 상세주소 <br> (9)role : 권한명(TC : 선생님) <br> (10)aprYn : 승인여부(0:미승인)"+
            "(11)enrollState : 재직상태(ENROLL : 재직중)")
    List<TeacherMngVo> teacherNotapprovedList(Long schoolId){
        return teacherMngService.teacherNotapprovedList(schoolId);
    }

}
