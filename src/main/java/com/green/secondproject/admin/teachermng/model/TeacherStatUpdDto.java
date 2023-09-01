package com.green.secondproject.admin.teachermng.model;

import com.green.secondproject.common.config.etc.EnrollState;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

@Data
public class TeacherStatUpdDto {
    private Long userId;
    private EnrollState enrollState;
    private int classNum;
    private int grade;

}
