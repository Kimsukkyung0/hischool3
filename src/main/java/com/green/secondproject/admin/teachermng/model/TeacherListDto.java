package com.green.secondproject.admin.teachermng.model;

import com.green.secondproject.common.config.etc.EnrollState;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

@Setter
public class TeacherListDto {
    private int page;
    private String search;
    private EnrollState enrollState;

    public int getPage() {
        return page;
    }
    public String getSearch() {
        return search;
    }
    public EnrollState getEnrollState() {
        return enrollState;
    }
}
