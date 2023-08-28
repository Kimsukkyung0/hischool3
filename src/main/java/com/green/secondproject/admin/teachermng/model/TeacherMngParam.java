package com.green.secondproject.admin.teachermng.model;

import com.green.secondproject.common.config.etc.EnrollState;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.VanEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TeacherMngParam {
    private RoleType roleType;
    private int aprYn;
    private List<VanEntity> vanEntityList;
    private EnrollState enrollState;


}
